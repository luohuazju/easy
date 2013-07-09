package com.sillycat.easysparkserver.model

import net.noerd.prequel.SQLFormatterImplicits._
import spark.Logging
import org.joda.time.DateTime
import net.noerd.prequel.Transaction

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/24/13
 * Time: 2:18 PM
 * To change this template use File | Settings | File Templates.
 */
case class Product(id: Option[Long], brand: String, productName: String, createDate: DateTime) extends Serializable

trait ProductsPrequel extends Logging{

   object Products {
     def create(implicit tx: Transaction){
         tx.execute(
           """create table if not exists PRODUCTS (
             |ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
             |BRAND VARCHAR(20),
             |PRODUCT_NAME VARCHAR(20),
             |CREATE_DATE TIMESTAMP(8)
             |)
           """.stripMargin)
         tx.commit()
     }

     def drop(implicit tx: Transaction){
          tx.execute("""drop table if exists PRODUCTS""")
     }

     def insertProduct(item: Product)(implicit tx: Transaction): Long = {
          tx.execute(
            "insert into PRODUCTS( BRAND, PRODUCT_NAME, CREATE_DATE) values ( ?, ?, ? )",
            item.brand, item.productName, item.createDate
          )
          tx.selectLong(
            """
              |SELECT LAST_INSERT_ID()
            """.stripMargin)
     }

     def loadProducts()(implicit tx: Transaction): Seq[Product] = {
         tx.select(
           """select
             |ID,
             |BRAND,
             |PRODUCT_NAME,
             |CREATE_DATE
             |from
             |PRODUCTS
           """.stripMargin){ r =>
           Product(r.nextLong,r.nextString.getOrElse(""),r.nextString.getOrElse(""),new DateTime(r.nextDate.get.getTime))
         }
     }

     def getProduct(id: Long)(implicit tx: Transaction): Option[Product] = {
          tx.selectHeadOption(
            """select
              |ID,
              |BRAND,
              |PRODUCT_NAME,
              |CREATE_DATE
              |from
              |PRODUCTS
              |where
              |ID = ?
            """.stripMargin, id){ r =>
            Product(r.nextLong,r.nextString.getOrElse(""),r.nextString.getOrElse(""),new DateTime(r.nextDate.get.getTime))
          }
     }

     def deleteProduct(id: Long)(implicit tx: Transaction): Unit = {
        tx.execute(
          """
            |delete from PRODUCTS
            |where ID = ?
          """.stripMargin, id)
     }

     def batchInsertProducts(products: Seq[Product])(implicit tx: Transaction): Unit = {
       tx.executeBatch("insert into PRODUCTS( BRAND, PRODUCT_NAME, CREATE_DATE) values ( ?, ?, ? )") { statement =>
           products.foreach { item =>
             statement.executeWith(item.brand,item.productName,item.createDate)
           }
       }
     }
   }
}