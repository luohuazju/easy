package com.sillycat.easysprayrestserver.actor

import akka.actor.{ Props, Actor }
import spray.routing._
import spray.routing.directives._
import spray.util.LoggingContext
import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport._
import shapeless._
import com.sillycat.easysprayrestserver.service.auth.UsersAuthenticationDirectives
import com.typesafe.scalalogging.slf4j.Logging
import akka.util.Timeout
import akka.actor.Actor
import spray.routing._
import spray.routing.directives._
import spray.util.LoggingContext
import spray.http.StatusCodes._
import shapeless._
import akka.util.Timeout
import spray.json._
import org.joda.time.format.DateTimeFormat
import org.joda.time.DateTime
import scala.Some
import spray.http._
import spray.http.MediaTypes._
import scala.Some
import shapeless.::
import akka.actor.{ Props, Actor }
import spray.routing._
import spray.routing.directives._
import spray.util.LoggingContext
import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport._
import shapeless._
import spray.routing.authentication._
import java.io.File
import org.parboiled.common.FileUtils
import java.io.BufferedInputStream
import java.io.FileInputStream
import com.typesafe.scalalogging.slf4j.Logging
import com.sillycat.easysprayrestserver.service.auth.BrandUserPassAuthenticator
import com.sillycat.easysprayrestserver.dao.BaseDAO

class URLRouterActor extends Actor with URLRouterService {
  def actorRefFactory = context
  def receive = runRoute(route)
}

//trait URLRouterService extends HttpService with UsersAuthenticationDirectives with Logging {
trait URLRouterService extends HttpService with Logging {

  implicit val timeout = Timeout(30 * 1000)
  
  implicit def myExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler.fromPF {
      case e: java.lang.IllegalArgumentException => ctx =>
        logger.error("Request {} could not be handled normally", ctx.request)
        ctx.complete(BadRequest, e.getMessage)
    }
  
  implicit val dao: BaseDAO = BaseDAO.apply

  def route = {
    pathPrefix(Version / BrandCode) { (apiVersion, brandCode) =>
      path("resource" / "all") {
        get {
          complete {
            logger.debug("Hitting the URI resource/all")
            "Morning, guest. apiVersion = " + apiVersion + ", brandCode =" + brandCode
          }
        }
      } ~
        path("resource" / "admin-only") {
          //authenticate(adminOnly) { user =>
          authenticate(BasicAuth(new BrandUserPassAuthenticator(dao), "Realm")) { user =>
            get {
              complete {
                logger.debug("Hitting the URI resource/admin-only")
                "Morning, " + user.userName + ". apiVersion = " + apiVersion + ", brandCode =" + brandCode
              }
            }
          }
        } ~
        path("resource" / "customer-only") {
          //authenticate(customerOnly) { user =>
          authenticate(BasicAuth(new BrandUserPassAuthenticator(dao), "Realm")) { user =>
            get {
              complete {
                logger.debug("Hitting the URI resource/customer-only")
                "Morning, " + user.userName + ". apiVersion = " + apiVersion + ", brandCode =" + brandCode
              }
            }
          }
        } ~
        path("resource" / "better-admin") {
          //authenticate(withRole("manager")) { user =>
          authenticate(BasicAuth(new BrandUserPassAuthenticator(dao), "Realm")) { user =>
            get {
              complete {
                logger.debug("Hitting the URI resource/better-admin")
                "Morning, " + user.userName + ". apiVersion = " + apiVersion + ", brandCode =" + brandCode
              }
            }
          }
        }
    }
  }

  val Version = PathMatcher("""v([0-9]+)""".r)
    .flatMap {
      case string :: HNil => {
        try Some(java.lang.Integer.parseInt(string) :: HNil)
        catch {
          case _: NumberFormatException => None
        }
      }
    }

  val BrandCode = PathElement
}