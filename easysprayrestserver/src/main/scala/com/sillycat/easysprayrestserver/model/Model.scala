package com.sillycat.easysprayrestserver.model

import org.joda.time.DateTime
import scala.slick.direct.AnnotationMapper.column
import com.sillycat.easysprayrestserver.dao.Profile
import scala.slick.util.Logging

case class Cart(id: Option[Long], cartName: String, cartType: CartType.Value, user: User, products: Seq[Product])

case class Product(id: Option[Long], productName: String, productDesn: String, createDate: DateTime, expirationDate: DateTime, productCode: String)

/**
 * 1 User(1, 'admin', ... , 'admin')
 * 2 User(2, 'manager', ..., 'manager')
 * 3 User(3, 'customer', ..., 'customer')
 */
case class User(id: Option[Long], userName: String, age: Int, userType: UserType.Value, createDate: DateTime, expirationDate: DateTime, password: String)


/**
 * 1 Role(1, "admin", "")
 * 2 Role(2, "manager", "")
 * 3 Role(3, "customer", "")
 */
case class Role(id: Option[Long], roleCode: String, description: String)

/**
 * 1. RUserRole(1, 1)
 * 2. RUserRole(2, 2)
 * 3. RUserRole(3, 3)
 */
case class RUserRole(roleId : Long, userId : Long)


case class RCartProduct(cartId : Long, productId : Long)
