package com.sillycat.easyspray.external

import spray.routing.SimpleRoutingApp
import spray.http.MediaTypes._
import scala.concurrent.duration._
import spray.util.LoggingContext
import spray.routing.ExceptionHandler
import spray.util.LoggingContext
import spray.http.StatusCodes._
import spray.routing._


object EasyRoutingApp extends App with SimpleRoutingApp {
  implicit def myExceptionHandler(implicit log: LoggingContext) =
  ExceptionHandler.fromPF {
    case e: ArithmeticException => ctx =>
      log.warning("Request {} could not be handled normally", ctx.request)
      ctx.complete(InternalServerError, "Bad numbers, bad result!!!")
  }
  
  startServer(interface = "localhost", port = 8080) {
    get {
      path("") {
        redirect("/hello")
      } ~
      path("hello") {
        respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <h1>Say hello to <em>spray</em> on <em>spray-can</em>!</h1>
              <p>(<a href="/stop?method=post">stop server</a>)</p>
            </html>
          }
        }
      }
    } ~
    (post | parameter('method ! "post")) {
      path("stop") {
        complete {
          system.scheduler.scheduleOnce(1 second span) {
            system.shutdown()
          }
          "Shutting down in 1 second..."
        }
      }
    }
  }
  
}