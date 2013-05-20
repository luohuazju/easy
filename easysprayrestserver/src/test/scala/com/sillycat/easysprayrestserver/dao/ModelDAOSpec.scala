package com.sillycat.easysprayrestserver.dao

import org.scalatest.BeforeAndAfterAll
import org.scalatest.FunSuite
import org.specs2.matcher.ShouldMatchers
import com.sillycat.easysprayrestserver.model._
import org.joda.time.DateTime
import scala.slick.session.Database.threadLocalSession

class ModelDAOSpec extends FunSuite with ShouldMatchers with BeforeAndAfterAll {

  implicit val dao: BaseDAO = BaseDAO.apply("test")

  override def beforeAll() { dao.create }

  override def afterAll() { dao.drop }

  test("Database tables are created and dropped") {
    assert("x" === "x")
  }
  
  test("Persist Product") {
    dao.db withSession {
      val item = new Product(None, "Iphone5", "Nice device", DateTime.now, DateTime.now, "IPHONE5")
      info(item.toString)
      val id = dao.Products.insert(item)
      assert(id === 1)
    }
  }
  
  test("Query Product Code") {
    dao.db withSession {
      val item = dao.Products.forProductCode("IPHONE5").get
      info(item.toString())
      assert(item.productCode === "IPHONE5")
    }
  }
  
  test("Persist User"){
    dao.db withSession {
      val item = new User(None, "sillycat", 31, UserType.ADMIN, DateTime.now , DateTime.now, "111111")
      info(item.toString)
      val id = dao.Users.insert(item)
      assert(id === 1)
    }
  }
  
  test("Fetch User"){
    dao.db withSession {
      val item = dao.Users.get(1)
      info(item.toString)
      assert(item.get.id.get === 1)
      assert(item.get.userName === "sillycat")
    }
  }
  
  test("Persist Cart"){
    dao.db withSession {
      //id: Option[Long], cartName: String, cartType: CartType.Value, user: User, products: Seq[Product]
      //val user = None
      //val products = None
      //val item = new Cart(None, "Sunday Cart", CartType.CHENGDU, user , products)
    }
  }
  
}