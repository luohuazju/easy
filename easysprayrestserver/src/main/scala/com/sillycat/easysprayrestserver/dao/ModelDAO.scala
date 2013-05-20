package com.sillycat.easysprayrestserver.dao

import scala.slick.util.Logging
import org.joda.time.DateTime
import com.sillycat.easysprayrestserver.model.Product
import com.sillycat.easysprayrestserver.model.Cart
import com.sillycat.easysprayrestserver.model.RCartProduct
import scala.slick.jdbc.meta.MTable
import com.sillycat.easysprayrestserver.model.User
import com.sillycat.easysprayrestserver.model.UserType
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
import org.joda.time.DateTime
import scala.slick.util.Logging
import scala.slick.jdbc.meta.MTable
import spray.json.{ RootJsonFormat, DefaultJsonProtocol }
import spray.httpx.SprayJsonSupport
import org.joda.time.format.DateTimeFormat
import scala.slick.ast._
import scala.slick.ast.Util._

trait UserDAO extends Logging { this: Profile =>
  import profile.simple._

  object Users extends Table[(Long, String, Int, String, DateTime, DateTime, String)]("USER") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc) // 1 This is the primary key column   
    def userName = column[String]("USER_NAME") // 2
    def age = column[Int]("AGE") //3
    def userType = column[String]("USER_TYPE") //4
    def createDate = column[DateTime]("CREATE_DATE") //5
    def expirationDate = column[DateTime]("EXPIRATION_DATE") // 6
    def password = column[String]("PASSWORD") // 7

    def * = id ~ userName ~ age ~ userType ~ createDate ~ expirationDate ~ password

    def persist(implicit session: Session) = id.? ~ userName ~ age ~ userType ~ createDate ~ expirationDate ~ password

    def insert(user: User)(implicit session: Session): Long = {
      val id = persist.insert(user.id, user.userName, user.age, user.userType.toString(), user.createDate, user.expirationDate, user.password)
      id
    }

    def auth(userName: String, password: String)(implicit session: Session): Option[User] = {
      logger.debug("I am authing the userName=" + userName + " password=" + password)
      (userName, password) match {
        case ("admin", "admin") =>
          Option(User(Some(1), "admin", 100, UserType.ADMIN, new DateTime(), new DateTime(), "admin"))
        case ("customer", "customer") =>
          Option(User(Some(2), "customer", 100, UserType.CUSTOMER, new DateTime(), new DateTime(), "customer"))
        case ("manager", "manager") =>
          Option(User(Some(3), "manager", 100, UserType.SELLER, new DateTime(), new DateTime(), "manager"))
        case _ => None
      }
    }
    
    def get(userId: Long)(implicit session: Session): Option[User] = {
      logger.debug("Try to fetch User Object with userId = " + userId)
      val query = for { item <- Users if (item.id === userId) } yield (item)
      logger.debug("Get User by id, SQL should be : " + query.selectStatement)
      query.firstOption map {
        case (user) => User(Option(user._1), user._2, user._3, UserType.withName(user._4), user._5, user._6, user._7)
      }
    }

    def create(implicit session: Session) = {
      if (!MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.create
        ddl.createStatements.foreach(println)
      }
    }

    def drop(implicit session: Session) = {
      if (MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.drop
        ddl.dropStatements.foreach(println)
      }
    }
  }
}

trait ProductDAO extends Logging { this: Profile =>
  import profile.simple._

  object Products extends Table[Product]("PRODUCT") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc) // 1 This is the primary key column   
    def productName = column[String]("PRODUCT_NAME") // 2
    def productDesn = column[String]("PRODUCT_DESN") //3
    def createDate = column[DateTime]("CREATE_DATE") //4
    def expirationDate = column[DateTime]("EXPIRATION_DATE") // 5
    def productCode = column[String]("PRODUCT_CODE") //6

    def * = id.? ~ productName ~ productDesn ~ createDate ~ expirationDate ~ productCode <> (Product.apply _, Product.unapply _)

    def forInsert = productName ~ productDesn ~ createDate ~ expirationDate ~ productCode <>
      ({ t => Product(None, t._1, t._2, t._3, t._4, t._5) },
        { (s: Product) => Some(s.productName, s.productDesn, s.createDate, s.expirationDate, s.productCode) })

    def insert(s: Product)(implicit session: Session): Long = {
      Products.forInsert returning id insert s
    }

    def forProductCode(productCode: String)(implicit session: Session): Option[Product] = {
      val query = for {
        item <- Products if item.productCode === productCode
      } yield item
      query.firstOption
    }
    
    def all()(implicit session: Session): Seq[Product] = {
      Query(Products).list
    }

    def create(implicit session: Session) = {
      if (!MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.create
        ddl.createStatements.foreach(println)
      }
    }

    def drop(implicit session: Session) = {
      if (MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.drop
        ddl.dropStatements.foreach(println)
      }
    }
  }
}

//Cart(id: Option[Long], cartName: String, cartType: CartType.Value, user: User, products: Seq[Product])
trait CartDAO extends Logging { this: Profile with RCartProductDAO =>
  import profile.simple._

  object Carts extends Table[(Long, String, String, Long)]("CART") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc) // 1 This is the primary key column   
    def cartName = column[String]("CART_NAME") // 2
    def cartType = column[String]("CART_TYPE") //3
    def userId = column[Long]("USER_ID") //5

    def * = id ~ cartName ~ cartType ~ userId

    def persist(implicit session: Session) = id.? ~ cartName ~ cartType ~ userId.?

    def insert(cart: Cart)(implicit session: Session): Long = {
      val cartId = persist.insert(cart.id, cart.cartName, cart.cartType.toString(), cart.user.id)
      RCartProducts.insertAll(cartId, cart.products)
      cartId
    }

    def create(implicit session: Session) = {
      if (!MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.create
        ddl.createStatements.foreach(println)
      }
    }

    def drop(implicit session: Session) = {
      if (MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.drop
        ddl.dropStatements.foreach(println)
      }
    }
  }
}

trait RCartProductDAO extends Logging { this: Profile =>
  import profile.simple._

  object RCartProducts extends Table[RCartProduct]("R_CART_PRODUCT") {
    def cartId = column[Long]("CART_ID", O.NotNull) // 1 This is the primary key column   
    def productId = column[Long]("PRODUCT_ID", O.NotNull) //2

    def * = cartId ~ productId <> (RCartProduct.apply _, RCartProduct.unapply _)

    def insertAll(cartId: Long, productIds: Seq[Product])(implicit session: Session): Unit = {
      productIds.foreach(x => RCartProducts.insert(RCartProduct(cartId, x.id.get)))
    }

    def create(implicit session: Session) = {
      if (!MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.create
        //ddl.createStatements.foreach(println)
      }
    }

    def drop(implicit session: Session) = {
      if (MTable.getTables(this.tableName).firstOption.isDefined) {
        val ddl = this.ddl
        ddl.drop
        //ddl.dropStatements.foreach(println)
      }
    }
  }
}

