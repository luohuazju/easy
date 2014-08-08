package com.sillycat.easysparkserver.temp

import spark.SparkContext
import spark.SparkContext._

object LocalSimpleJob extends App {
  val logFile = "/var/log/apache2" // Should be some file on your system
  val sc = new SparkContext("local[2]",
      "Simple Job", 
      "/opt/spark",
      List("target/easysparkserver-assembly-1.0.jar"),
      //List("target/scala-2.10/easysparkserver_2.10-1.0.jar"),
      //List("target/scala-2.9.2/easysparkserver_2.9.2-1.0.jar"), 
      Map())
  val logData = sc.textFile(logFile, 2).cache()
  val numAs = logData.filter(line => line.contains("a")).count()
  val numBs = logData.filter(line => line.contains("b")).count()
  println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))

  sc.stop
}