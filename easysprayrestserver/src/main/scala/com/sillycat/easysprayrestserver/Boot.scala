package com.sillycat.easysprayrestserver

import spray.can.server.SprayCanHttpServerApp
import akka.actor.Props
import com.sillycat.easysprayrestserver.bootstrap.Bootstrap
import com.sillycat.easysprayrestserver.actors.URLRouterActor

object Boot extends App with Bootstrap with SprayCanHttpServerApp{

  val handler = system.actorOf(Props[URLRouterActor])

  val serverAddress: String = config.getString("server.address")
  val serverPort: Int = config.getInt("server.port")
  
  newHttpServer(handler) ! Bind(interface = serverAddress, port = serverPort)

}