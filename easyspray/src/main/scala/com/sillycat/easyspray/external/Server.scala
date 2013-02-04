package com.sillycat.easyspray.external

import spray.can.server.SprayCanHttpServerApp
import akka.actor.Props

object Server extends App with SprayCanHttpServerApp {
  val handler = system.actorOf(Props[EasyRouter])
  newHttpServer(handler) ! Bind(interface = "localhost", port = 9000)
}