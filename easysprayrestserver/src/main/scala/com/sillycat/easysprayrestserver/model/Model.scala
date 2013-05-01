package com.sillycat.easysprayrestserver.model

import org.joda.time.DateTime
import scala.slick.direct.AnnotationMapper.column
import com.sillycat.easysprayrestserver.dao.Profile
import scala.slick.util.Logging

case class Cart(id: Option[Long], cartName: String, cartType: CartType.Value, user: User, products: Seq[Product])

case class Product(id: Option[Long], productName: String, productDesn: String, createDate: DateTime, expirationDate: DateTime)

case class User(id: Option[Long], userName: String, age: Int, userType: UserType.Value, createDate: DateTime, expirationDate: DateTime)
