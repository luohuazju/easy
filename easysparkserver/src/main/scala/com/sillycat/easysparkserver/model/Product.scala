package com.sillycat.easysparkserver.model

import net.noerd.prequel.SQLFormatterImplicits._
import net.noerd.prequel.DatabaseConfig
import spark.Logging
import org.joda.time.DateTime

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/24/13
 * Time: 2:18 PM
 * To change this template use File | Settings | File Templates.
 */
case class Product(id: Option[Long], brand: String, productName: String, createDate: DateTime)

trait ProductsPrequel extends Logging{

   object Products {
     def create(implicit database: DatabaseConfig){
       database transaction { tx =>
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
     }

     def drop(implicit database: DatabaseConfig){
       database transaction { tx =>
          tx.execute("""drop table if exists PRODUCTS""")
       }
     }

     def insertProduct(item: Product)(implicit database: DatabaseConfig): Long = {
        database transaction { tx =>
          tx.execute(
            "insert into PRODUCTS( BRAND, PRODUCT_NAME, CREATE_DATE) values ( ?, ?, ? )",
            item.brand, item.productName, item.createDate
          )
          tx.selectLong(
            """
              |SELECT LAST_INSERT_ID()
            """.stripMargin)
        }
     }

     def loadProducts()(implicit database: DatabaseConfig): Seq[Product] = {
       database transaction { tx =>
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
     }

     def getProduct(id: Long)(implicit database: DatabaseConfig): Option[Product] = {
        database transaction { tx =>
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
     }
   }
}