package com.sillycat.easysprayrestserver

import spray.can.server.SprayCanHttpServerApp
import akka.actor.Props
import com.sillycat.easysprayrestserver.bootstrap.Bootstrap
import com.sillycat.easysprayrestserver.actor.URLRouterActor

object Boot extends App with Bootstrap with SprayCanHttpServerApp {

  val handler = system.actorOf(Props[URLRouterActor])

  val env = config.getString("build.env")
  var serverAddress: String = config.getString("server.address")
  var serverPort: Int = config.getInt("server.port")

  if (env != null && env != "") {
    serverAddress = if (config.getString("environment." + env + ".server.address") != null) config.getString("environment." + env + ".server.address") else serverAddress
    serverPort = if (config.getInt("environment." + env + ".server.port") != 0) config.getInt("environment." + env + ".server.port") else serverPort
  }

  newHttpServer(handler) ! Bind(interface = serverAddress, port = serverPort)

}