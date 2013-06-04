package com.sillycat.easyspray.sprayclient

import spray.client._
import scala.concurrent.Future
import akka.actor._
import spray.can.client.HttpClient
import spray.client.HttpConduit
import spray.io._
import spray.util._
import spray.http._
import HttpMethods._
import scala.util.{Success, Failure}
import scala.concurrent.duration._
import akka.actor.{Props, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
import akka.event.Logging
import spray.json.{JsonFormat, DefaultJsonProtocol}
import spray.httpx.SprayJsonSupport
import spray.http._
import spray.util._

object SprayClientApp extends App  {

  implicit val system = ActorSystem()
  val ioBridge = IOExtension(system).ioBridge()
  val httpClient = system.actorOf(Props(new HttpClient(ioBridge)))

  val conduit = system.actorOf(
    props = Props(new HttpConduit(httpClient, "maps.googleapis.com", 80)),
    name = "http-conduit")
    
  val pipeline = HttpConduit.sendReceive(conduit)
  
  val response: Future[HttpResponse] = pipeline(HttpRequest(method = GET, uri = "/maps/api/elevation/json?locations=27.988056,86.925278&sensor=false"))

  response onComplete {
    case Success(responseContent) =>
      println("The elevation of Mt. Everest is: {} m" + responseContent)
      shutdown()

    case Failure(error) =>
      println("Couldn't get elevation")
      shutdown()
  }

  def shutdown(): Unit = {
   
    system.shutdown()
  }

}