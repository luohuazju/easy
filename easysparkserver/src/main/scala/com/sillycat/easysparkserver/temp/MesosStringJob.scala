package com.sillycat.easysparkserver.temp

import spark.SparkContext
import com.sillycat.easysparkserver.model.Product
import scala.Product
import org.joda.time.DateTime

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 7/19/13
 * Time: 5:10 PM
 * To change this template use File | Settings | File Templates.
 */
object MesosStringJob extends App{
  //val sparkMaster = "local"
  var sparkMaster = "mesos://localhost:5050"
  val sc = new SparkContext(sparkMaster,
    "Complex Job",
    "/opt/spark",
    List("/Users/carl/work/easy/easysparkserver/target/easysparkserver-assembly-1.0.jar"),
    Map())

  val products = Seq("mike","carl","kiko","nick")

  val name = "k"

  val rdd = sc.makeRDD(products,2)

  val rdd2 = rdd.groupBy{ s => s.contains("k")}

  rdd2.toArray().foreach { arrays =>
    arrays._2.foreach { item =>
      println("Products are " + arrays._1 +  " ===============" + item)
    }
  }

  sc.stop
}
