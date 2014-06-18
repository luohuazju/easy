// package com.sillycat.easysparkserver.model

// import org.scalatest.FunSuite
// import org.specs2.matcher.ShouldMatchers
// import org.scalatest.BeforeAndAfterAll
// import com.sillycat.easysparkserver.dao.PrequelDAO
// import org.joda.time.DateTime

// /**
//  * Created with IntelliJ IDEA.
//  * User: carl
//  * Date: 6/27/13
//  * Time: 10:22 AM
//  * To change this template use File | Settings | File Templates.
//  */
// class ProductPrequelSpec extends FunSuite with ShouldMatchers with BeforeAndAfterAll {

//   val dao = PrequelDAO

//   override def beforeAll() {
//     dao.create
//   }

//   override def afterAll() {
//     dao.drop
//   }

//   test("Database tables are created and dropped") {
//     assert("x" === "x")
//   }

//   test("Verify Products Insert Operation"){
//     val item = Product(None,"CK","good things",DateTime.now)
//     dao.testDatabase transaction { implicit tx =>
//       val id = dao.Products.insertProduct(item)
//       assert(id === 1)
//     }
//   }

//   test("Verify Query Products Operation"){
//     dao.testDatabase transaction { implicit tx =>
//       val items = dao.Products.loadProducts
//       assert(items != Nil)
//       assert(items.size === 1)
//     }
//   }

//   test("Verify Get Product Operation"){
//     dao.testDatabase transaction { implicit tx =>
//       val item = dao.Products.getProduct(1)
//       assert(item != None)
//       assert(item.get.id.get === 1)
//       assert(item.get.brand === "CK")
//     }
//   }

//   test("Verify delete Product Operation"){
//     dao.testDatabase transaction { implicit tx =>
//       dao.Products.deleteProduct(1)
//       val item = dao.Products.getProduct(1)
//       assert(item === None)
//     }
//   }

//   test("Verify batch Insert Operation"){
//     dao.testDatabase transaction { implicit tx =>
//       val items = Seq(Product(None,"CK1","good things1",DateTime.now), Product(None,"CK2","good things2",DateTime.now))
//       dao.Products.batchInsertProducts(items)
//       val return_items = dao.Products.loadProducts()
//       assert(return_items.size === 2)
//     }
//   }

// }
