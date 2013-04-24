package com.sillycat.easysprayrestserver.actors

import scala.concurrent.duration._
import akka.pattern.ask
import akka.util.Timeout
import akka.actor._
import spray.io.{IOBridge, IOExtension}
import spray.can.server.HttpServer
import spray.util._
import spray.http._
import HttpMethods._
import MediaTypes._

class URLRouterActor extends Actor with SprayActorLogging {

  implicit val timeout: Timeout = Duration(1, "sec")
  
  def receive = {
    case HttpRequest(HttpMethods.GET, "/", _, _, _) =>
      sender ! index
  }    
      
  lazy val index = HttpResponse(
    entity = HttpBody(`text/html`,
      <html>
        <body>
          <h1>Welcome Page</h1>
        </body>
      </html>.toString))
}