package com.sillycat.easysparkserver.model

import org.scalatest.FunSuite
import org.specs2.matcher.ShouldMatchers
import org.scalatest.BeforeAndAfterAll
import com.sillycat.easysparkserver.dao.PrequelDAO
import org.joda.time.DateTime
import net.noerd.prequel.DatabaseConfig

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/27/13
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */
class ProductPrequelSpec extends FunSuite with ShouldMatchers with BeforeAndAfterAll {

  val dao = PrequelDAO

  override def beforeAll() {
    dao.create
  }

  override def afterAll() {
    dao.drop
  }

  test("Database tables are created and dropped") {
    assert("x" === "x")
  }

  test("Verify Products Insert Operation"){
    val item = Product(None,"CK","good things",DateTime.now)
    val id = dao.Products.insertProduct(item)(dao.testDatabase)
    assert(id === 1)
  }

}
