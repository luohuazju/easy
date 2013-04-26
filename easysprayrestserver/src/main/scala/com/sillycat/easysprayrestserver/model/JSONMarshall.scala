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


object CartJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {
  
  implicit object CartJsonFormat extends RootJsonFormat[Cart] {
    def write(cart: Cart) = JsObject(
      Map(
      "cartName" -> JsString(cart.cartName),
      "cartType" -> JsString(cart.cartType.toString()),
      "user" -> JsObject(Map(
    		  			"userName" -> JsString(cart.user.userName) 
    		  		) ++
    		  			cart.user.id.map( i => Map("id" -> JsNumber(i))).getOrElse(Map[String, JsValue]())
    		  		)
      ) ++
      cart.id.map( i => Map("id" -> JsNumber(i))).getOrElse(Map[String, JsValue]())
    )
    def read(jsCart: JsValue) = {
//      jsCart.asJsObject.getFields("id", "cartName", "cartType", "user") match {
//        case Seq(JsNumber(id), JsString(cartName), JsString(cartType), JsObject(user)) =>
//          
//          val userObject = new User(None,"Carl",0,null,null,null)
//          new Cart(Option(id.longValue), cartName, CartType.withName(cartType), userObject, null)
//        case _ => throw new DeserializationException("Cart expected")
//      }
    	val params: Map[String, JsValue] = jsCart.asJsObject.fields
    	val userParams  = params("user").convertTo[JsValue].asJsObject.fields
    	Cart(
    	    params.get("id").map(_.convertTo[Int]), 
    	    params("cartName").convertTo[String],
    	    CartType.withName(params("cartType").convertTo[String]),
    	    User(
    	    		userParams.get("id").map(_.convertTo[Int]), 
    	    		userParams("userName").convertTo[String], 
    	    		0, 
    	    		null,
    	    		null,
    	    		null
    	    	),
    	    null
    	)
    }
  }
}

object UserJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {
  private val dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")
  implicit object UserJsonFormat extends RootJsonFormat[User] {
    def write(user: User) = JsObject(
      Map(  
      "userName" -> JsString(user.userName),
      "age" -> JsNumber(user.age),
      "userType" -> JsString(user.userType.toString()),
      "createDate" -> JsString(dateTimeFormat.print(new DateTime(user.createDate))),
      "expirationDate" -> JsString(dateTimeFormat.print(new DateTime(user.expirationDate)))
      ) ++ 
      user.id.map( i => Map("id" -> JsNumber(i))).getOrElse(Map[String, JsValue]()) 
    )
    def read(jsUser: JsValue) = {
      jsUser.asJsObject.getFields("id", "userName", "age", "userType", "createDate", "expirationDate") match {
        case Seq(JsNumber(id), JsString(userName), JsNumber(age), JsString(userType), JsString(createDate), JsString(expirationDate)) =>
          val createDateObject = dateTimeFormat.parseDateTime(createDate)
          val expirationDateObject = dateTimeFormat.parseDateTime(expirationDate)
          new User(Some(id.longValue), userName, age.toInt, UserType.withName(userType), createDateObject, expirationDateObject)
        case Seq(JsString(userName), JsNumber(age), JsString(userType), JsString(createDate), JsString(expirationDate)) =>
          val createDateObject = dateTimeFormat.parseDateTime(createDate)
          val expirationDateObject = dateTimeFormat.parseDateTime(expirationDate)
          new User( None, userName, age.toInt, UserType.withName(userType), createDateObject, expirationDateObject)
        case _ => throw new DeserializationException("User expected")
      }
    }
  }
}

object ProductJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {
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