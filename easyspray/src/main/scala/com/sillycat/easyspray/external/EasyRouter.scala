package com.sillycat.easyspray.external

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

class EasyRouter extends Actor with SprayActorLogging {
  implicit val timeout: Timeout = Duration(1, "sec")

  def receive = {
    case HttpRequest(HttpMethods.GET, "/", _, _, _) =>
      sender ! index
    case HttpRequest(HttpMethods.GET, "/stop", _, _, _) =>
      sender ! HttpResponse(entity = "Shutting down in 1 second ...")
      context.system.scheduler.scheduleOnce(1 second span, new Runnable { def run() { context.system.shutdown() } })
    case HttpRequest(GET, "/server-stats", _, _, _) =>
      val client = sender
      (context.actorFor("../http-server") ? HttpServer.GetStats).onSuccess {
        case x: HttpServer.Stats => client ! statsPresentation(x)
      }

    case HttpRequest(GET, "/io-stats", _, _, _) =>
      val client = sender
      (IOExtension(context.system).ioBridge() ? IOBridge.GetStats).onSuccess {
        case IOBridge.StatsMap(map) => client ! statsPresentation(map)
      }
  }

  lazy val index = HttpResponse(
    entity = HttpBody(`text/html`,
      <html>
        <body>
          <h1>Welcome Page</h1>
    	  <ul>
    		<li><a href="/stop">Stop The Server</a></li>
    		<li><a href="/server-stats">Server Status</a></li>
    		<li><a href="/io-stats">IO Status</a></li>
          </ul>
        </body>
      </html>.toString))
      
  def statsPresentation(s: HttpServer.Stats) = HttpResponse(
    entity = HttpBody(`text/html`,
      <html>
        <body>
          <h1>HttpServer Stats</h1>
          <table>
            <tr><td>uptime:</td><td>{s.uptime.formatHMS}</td></tr>
            <tr><td>totalRequests:</td><td>{s.totalRequests}</td></tr>
            <tr><td>openRequests:</td><td>{s.openRequests}</td></tr>
            <tr><td>maxOpenRequests:</td><td>{s.maxOpenRequests}</td></tr>
            <tr><td>totalConnections:</td><td>{s.totalConnections}</td></tr>
            <tr><td>openConnections:</td><td>{s.openConnections}</td></tr>
            <tr><td>maxOpenConnections:</td><td>{s.maxOpenConnections}</td></tr>
            <tr><td>requestTimeouts:</td><td>{s.requestTimeouts}</td></tr>
            <tr><td>idleTimeouts:</td><td>{s.idleTimeouts}</td></tr>
          </table>
        </body>
      </html>.toString
    )
  )
  
  def statsPresentation(map: Map[ActorRef, IOBridge.Stats]) = HttpResponse(
    entity = HttpBody(`text/html`,
      <html>
        <body>
          <h1>IOBridge Stats</h1>
          <table>
            {
              def extractData(t: (ActorRef, IOBridge.Stats)) = t._1.path.elements.last :: t._2.productIterator.toList
              val data = map.toSeq.map(extractData).sortBy(_.head.toString).transpose
              val headers = Seq("IOBridge", "uptime", "bytesRead", "bytesWritten", "connectionsOpened", "commandsExecuted")
              headers.zip(data).map { case (header, items) =>
                <tr><td>{header}:</td>{items.map(x => <td>{x}</td>)}</tr>
              }
            }
          </table>
        </body>
      </html>.toString
    )
  )
}