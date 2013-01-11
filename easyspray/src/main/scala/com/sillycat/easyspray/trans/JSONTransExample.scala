package com.sillycat.easyspray.trans

import spray.json._
import spray.httpx.SprayJsonSupport

object JSONTransExample extends App {

  object EnumSex extends Enumeration {
    type Sex = Value
    val MALE = Value("MALE")
    val FEMALE = Value("FEMALE")
  }

  case class Address(streetNumber: String, street: String, city: String)

  case class Person(userName: String, age: Int, sex: EnumSex.Sex, address: Address)

  case class Color(name: String, red: Int, green: Int, blue: Int)

  object ColorProtocol extends DefaultJsonProtocol {
    implicit val colorFormat = jsonFormat4(Color)
  }

  object AddressJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {
    implicit object AddressJsonFormat extends RootJsonFormat[Address] {
      def write(address: Address) = JsObject(
        "streetNumber" -> JsString(address.streetNumber),
        "street" -> JsString(address.street),
        "city" -> JsString(address.city))
      def read(jsAddress: JsValue) = {
        jsAddress.asJsObject.getFields("streetNumber", "street", "city") match {
          case Seq(JsString(streetNumber), JsString(street), JsString(city)) =>
            new Address(streetNumber, street, city)
          case _ => throw new DeserializationException("Address expected")
        }
      }
    }
  }

  val json1 = """{ "streetNumber": "A1", "street" : "Main Street", "city" : "Colombo" }"""
  val json2 = """{ "name" : "John", "age" : 26,  "sex" : 0 , "address" : { "streetNumber": "A1", "street" : "Main Street", "city" : "Colombo" }}"""

  //string to AST
  val addressAST = json1.asJson //or JsonParser(source)
  val personAST = json2.asJson

  //AST to string
  println(addressAST.compactPrint) //or .compactPrint
  println(personAST.prettyPrint)
  
  import AddressJsonProtocol._
  //AST 2 Object
  val address1 = addressAST.convertTo[Address]
  
  //Object 2 AST
  val addressAST2 = address1.toJson

  import ColorProtocol._
  //Object 2 AST
  val colorAST = Color("CadetBlue", 95, 158, 160).toJson

  //AST 2 Object
  val color1 = colorAST.convertTo[Color]

}


