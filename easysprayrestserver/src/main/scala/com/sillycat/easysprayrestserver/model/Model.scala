package com.sillycat.easysprayrestserver.model

import org.joda.time.DateTime
import spray.json._
import DefaultJsonProtocol._

case class Cart(id: Option[Long], cartName: String, cartType: CartType.Value, user: User,  products: Seq[Product])

case class Product(id: Option[Long],  productName: String, productDesn: String, createDate: DateTime, expirationDate: DateTime)

case class User(id: Option[Long], userName: String, age: Int, userType: UserType.Value, createDate: DateTime, expirationDate: DateTime)