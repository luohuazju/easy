package com.sillycat.easysparkserver.temp

import spark.SparkContext
import com.sillycat.easysparkserver.model.Product
import org.joda.time.DateTime

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 7/1/13
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
object ComplexJob extends App{
  val sc = new SparkContext("local",
    "Complex Job",
    "/opt/spark",
    List("target/scala-2.10/easysparkserver_2.10-1.0.jar"),
    Map())

  val item1 = Product(None,"CK","good",DateTime.now)
  val item2 = Product(None,"apple","good",DateTime.now)
  val item3 = Product(None,"nike","nice",DateTime.now)
  val item4 = Product(None,"cat","bad",DateTime.now)
  val products = Seq(item1,item2,item3,item4)

  val name = "good"

  val rdd = sc.makeRDD(products,2)

  //val rdd2 = rdd.filter{ s => s.productName == name }

  rdd.foreach{  s =>
     if(s.productName == name){
       println("Products are good = " + s)
     }
  }

  sc.stop
}
