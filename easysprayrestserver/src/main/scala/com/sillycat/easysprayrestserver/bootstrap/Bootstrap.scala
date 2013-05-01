package com.sillycat.easysprayrestserver.bootstrap

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

trait Bootstrap {
  
  //implicit val system = ActorSystem("RESTAPIService")
  
  val config = ConfigFactory.load()
  
  val env = config.getString("build.env")
  var serverAddress: String = config.getString("server.address")
  var serverPort: Int = config.getInt("server.port")

  if (env != null && env != "") {
    serverAddress = if (config.getString("environment." + env + ".server.address") != null) config.getString("environment." + env + ".server.address") else serverAddress
    serverPort = if (config.getInt("environment." + env + ".server.port") != 0) config.getInt("environment." + env + ".server.port") else serverPort
  }

}