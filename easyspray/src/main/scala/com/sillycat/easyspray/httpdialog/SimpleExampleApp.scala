package com.sillycat.easyspray.httpdialog

import java.util.concurrent.TimeUnit._
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import spray.can.client.{ HttpDialog, HttpClient }
import spray.io._
import spray.http._
import HttpMethods._
import akka.actor.ActorSystem
import akka.actor.Props
import spray.httpx.RequestBuilding._

object SimpleExampleApp extends App {

  implicit val system = ActorSystem("simple-example")
  import system.log

  val ioBridge = IOExtension(system).ioBridge()
  val client = system.actorOf(Props(new HttpClient(ioBridge)))

  //simple request
  val response: Future[HttpResponse] =
    HttpDialog(client, "www.google.com", 80)
      .send(HttpRequest(method = GET, uri = "/"))
      .end
  //multiple requests, fire one by one
  val responses1: Future[Seq[HttpResponse]] =
    HttpDialog(client, "www.google.com", 80)
      .send(Post("/shout", "yeah!"))
      .awaitResponse
      .send(Put("/count", "42"))
      .end

  //multiple requests, fire at same time
  val responses2: Future[Seq[HttpResponse]] =
    HttpDialog(client, "www.google.com", 80)
      .send(Get("/img/a.gif"))
      .send(Get("/img/b.gif"))
      .send(Get("/img/c.gif"))
      .end

  //multiple requests, fire follow this request -> response ->request
  val response3: Future[HttpResponse] =
    HttpDialog(client, "www.google.com", 80)
      .send(Get("/ping"))
      .reply(response => Get("/ping2", response.entity))
      .end
}