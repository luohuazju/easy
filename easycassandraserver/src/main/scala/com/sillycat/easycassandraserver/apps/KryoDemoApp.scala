package com.sillycat.easycassandraserver.apps

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.{Input, Output}
import java.util.{ HashMap => JMap }

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 7/2/13
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
object KryoDemoApp extends App{

  //java HashMap
  val maps = new JMap[String, Any]()
  maps.put("productName", "iphone4s")
  maps.put("price", 21.33)

  val kryo = new Kryo


  //transfer HashMap to bytes
  val out1 = new Output(new Array[Byte](4096))
  kryo.writeObject(out1, maps)
  out1.close
  val outputBytes1 = out1.toBytes
  println("outputBytes1 of Map = " + outputBytes1)

  //read the bytes back to HashMap
  val in1 = new Input(outputBytes1)
  val outputMap1 = kryo.readObject(in1,classOf[JMap[String, Any]])
  in1.close

  println("outputMap1 of Map = " + outputMap1)

}
