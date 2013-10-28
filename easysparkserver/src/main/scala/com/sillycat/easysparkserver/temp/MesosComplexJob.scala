package com.sillycat.easysparkserver.temp

import spark.SparkContext
import com.sillycat.easysparkserver.model.Product
import scala.Product
import org.joda.time.DateTime

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 7/18/13
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
object MesosComplexJob extends App{
  //val sparkMaster = "local"
  //val sparkMaster = "spark://localhost:7077"
  var sparkMaster = "mesos://localhost:5050"
  val sc = new SparkContext(sparkMaster,
    "Complex Job",
    "/opt/spark",
    List("/Users/carl/work/easy/easysparkserver/target/easysparkserver-assembly-1.0.jar"),
    Map())

  val item1 = Product(None,"CK","good",DateTime.now)
  val item2 = Product(None,"apple","good",DateTime.now)
  val item3 = Product(None,"nike","bad",DateTime.now)
  val item4 = Product(None,"cat","bad",DateTime.now)
  val products = Seq(item1,item2,item3,item4)

  val name = "good"

  val rdd = sc.makeRDD(products,2)

  val rdd2 = rdd.groupBy{ s => s.productName == name}

  rdd2.toArray().foreach { arrays =>
    arrays._2.foreach { item =>
      println("Products are " + arrays._1 +  " ===============" + item)
    }
  }

  sc.stop
}
