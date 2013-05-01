package com.sillycat.easysprayrestserver.dao

import scala.slick.util.Logging
import org.joda.time.DateTime
import com.sillycat.easysprayrestserver.model.Product
import com.sillycat.easysprayrestserver.model.Cart
import scala.slick.jdbc.meta.MTable
import com.sillycat.easysprayrestserver.model.User
import com.sillycat.easysprayrestserver.model.UserType


trait UserDAO extends Logging { this: Profile =>
  import profile.simple._

  object Users extends Table[Long]("USER") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc) // 1 This is the primary key column   
    def userName = column[String]("USER_NAME") // 2
    def age = column[Int]("AGE") //3
    def userType = column[String]("USER_TYPE") //4
    def createDate = column[DateTime]("CREATE_DATE") //5
    def expirationDate = column[DateTime]("EXPIRATION_DATE") // 6
    def password = column[DateTime]("PASSWORD") // 7

    def * = id
    
    def auth(userName: String, password: String)(implicit session: Session) : Option[User] = {
      (userName, password) match {
        case ("admin","admin") => Option(User(Some(1), "admin", 100, UserType.ADMIN, new DateTime(), new DateTime(),"admin"))
        case ("customer","customer") => Option(User(Some(2), "customer", 100, UserType.CUSTOMER, new DateTime(), new DateTime(),"customer"))
        case ("manager","manager") => Option(User(Some(3), "manager", 100, UserType.SELLER, new DateTime(), new DateTime(),"manager"))
        case _ => None
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
    def createDate = column[DateTime]("CREATE_DATE") //5
    def expirationDate = column[DateTime]("EXPIRATION_DATE") // 6

    def * = id.? ~ productName ~ productDesn ~ createDate ~ expirationDate <> (Product.apply _, Product.unapply _)
    
    def forInsert = productName ~ productDesn ~ createDate ~ expirationDate <>
      ({ t => Product(None, t._1, t._2, t._3, t._4) },
        { (s: Product) => Some(s.productName, s.productDesn, s.createDate, s.expirationDate) })

    def insert(s: Product)(implicit session: Session): Long = {
      Products.forInsert returning id insert s
    }

    def create(implicit session: Session) = {
      if (!MTable.getTables(this.tableName).firstOption.isDefined) {
        this.ddl.create
      }
    }

    def drop(implicit session: Session) = {
      if (MTable.getTables(this.tableName).firstOption.isDefined) {
        this.ddl.drop
      }
    }
  }
}

//Cart(id: Option[Long], cartName: String, cartType: CartType.Value, user: User, products: Seq[Product])
trait CartDAO extends Logging { this: Profile =>
  import profile.simple._
  
  object Carts extends Table[Long]("CART") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc) // 1 This is the primary key column   
    def cartName = column[String]("CART_NAME") // 2
    def cartType = column[String]("CART_TYPE") //3
    def userId = column[Long]("USER_ID") //5

    def * = id
  }
}
