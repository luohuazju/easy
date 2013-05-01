package com.sillycat.easysprayrestserver.model

import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import spray.json.DeserializationException
import spray.json.JsNumber
import spray.json.JsObject
import spray.json.JsString
import spray.json.JsValue
import spray.json.RootJsonFormat
import org.joda.time.format.DateTimeFormat
import org.joda.time.DateTime
import spray.json.JsArray
import spray.json._
import DefaultJsonProtocol._



class UserJsonProtocol(currentId: Long) extends DefaultJsonProtocol {
  private val dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")
  implicit object UserJsonFormat extends RootJsonFormat[User] {
    def write(user: User) = JsObject(
      Map(  
      "userName" -> JsString(user.userName),
      "password" -> JsString(user.password),
      "age" -> JsNumber(user.age),
      "userType" -> JsString(user.userType.toString()),
      "createDate" -> JsString(dateTimeFormat.print(new DateTime(user.createDate))),
      "expirationDate" -> JsString(dateTimeFormat.print(new DateTime(user.expirationDate)))
      ) ++ 
      user.id.map( i => Map("id" -> JsNumber(i))).getOrElse(Map[String, JsValue]()) 
    )
    def read(jsUser: JsValue) = {
      jsUser.asJsObject.getFields("id", "userName", "age", "userType", "createDate", "expirationDate", "password") match {
        case Seq(JsNumber(id), JsString(userName), JsNumber(age), JsString(userType), JsString(createDate), JsString(expirationDate), JsString(password)) =>
          val createDateObject = dateTimeFormat.parseDateTime(createDate)
          val expirationDateObject = dateTimeFormat.parseDateTime(expirationDate)
          new User(Some(id.longValue), userName, age.toInt, UserType.withName(userType), createDateObject, expirationDateObject,password)
        case Seq(JsString(userName), JsNumber(age), JsString(userType), JsString(createDate), JsString(expirationDate), JsString(password)) =>
          val createDateObject = dateTimeFormat.parseDateTime(createDate)
          val expirationDateObject = dateTimeFormat.parseDateTime(expirationDate)
          new User( None, userName, age.toInt, UserType.withName(userType), createDateObject, expirationDateObject,password)
        case _ => throw new DeserializationException("User expected")
      }
    }
  }
}

object ProductJsonProtocol extends DefaultJsonProtocol {
  private val dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")
  implicit object ProductJsonFormat extends RootJsonFormat[Product] {
    def write(product: Product) = JsObject(
      Map(
      "productName" -> JsString(product.productName),
      "productDesn" -> JsString(product.productDesn),
      "createDate"	-> JsString(dateTimeFormat.print(new DateTime(product.createDate))),
      "expirationDate" -> JsString(dateTimeFormat.print(new DateTime(product.expirationDate)))
      ) ++
      product.id.map( i => Map("id" -> JsNumber(i))).getOrElse(Map[String, JsValue]()) 
      )
    def read(jsProduct: JsValue) = {
      jsProduct.asJsObject.getFields("id", "productName", "productDesn", "createDate", "expirationDate") match {
        case Seq(JsNumber(id), JsString(productName), JsString(productDesn), JsString(createDate), JsString(expirationDate)) =>
          val createDateObject = dateTimeFormat.parseDateTime(createDate)
          val expirationDateObject = dateTimeFormat.parseDateTime(expirationDate)
          new Product(Some(id.longValue),  productName, productDesn, createDateObject, expirationDateObject)
        case Seq(JsString(productName), JsString(productDesn), JsString(createDate), JsString(expirationDate)) =>
          val createDateObject = dateTimeFormat.parseDateTime(createDate)
          val expirationDateObject = dateTimeFormat.parseDateTime(expirationDate)
          new Product(None,  productName, productDesn, createDateObject, expirationDateObject)
        case _ => throw new DeserializationException("Product expected")
      }
    }
  }
}

object CartJsonProtocol extends DefaultJsonProtocol {
  
  implicit object CartJsonFormat extends RootJsonFormat[Cart] {
  implicit val userFormatter = (new UserJsonProtocol(1)).UserJsonFormat
  implicit val productFormatter = ProductJsonProtocol.ProductJsonFormat
    def write(cart: Cart) = JsObject(
      Map(
      "cartName" -> JsString(cart.cartName),
      "cartType" -> JsString(cart.cartType.toString()),
      "user"     -> cart.user.toJson,
      "products" -> cart.products.toJson
      ) ++
      cart.id.map( i => Map("id" -> JsNumber(i))).getOrElse(Map[String, JsValue]())
    )
    def read(jsCart: JsValue) = {
    	val params: Map[String, JsValue] = jsCart.asJsObject.fields
    	Cart(
    	    params.get("id").map(_.convertTo[Int]), 
    	    params("cartName").convertTo[String],
    	    CartType.withName(params("cartType").convertTo[String]),
    	    params("user").convertTo[User],
    	    params("products").convertTo[Seq[Product]]
    	)
    }
  }
}