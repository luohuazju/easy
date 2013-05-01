package com.sillycat.easysprayrestserver.actor

import akka.actor.{ Props, Actor }
import spray.routing._
import spray.routing.directives._
import spray.util.LoggingContext
import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport._
import shapeless._
import com.sillycat.easysprayrestserver.service.auth.UsersAuthenticationDirectives

class URLRouterActor extends Actor with URLRouterService {
  def actorRefFactory = context
  def receive = runRoute(route)
}

trait URLRouterService extends HttpService with UsersAuthenticationDirectives {

  implicit def myExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler.fromPF {
      case e: java.lang.IllegalArgumentException => ctx =>
        log.warning("Request {} could not be handled normally", ctx.request)
        ctx.complete(BadRequest, e.getMessage)
    }

  def route = {
    pathPrefix(Version / BrandCode) { (apiVersion, brandCode) =>
      path("resource" / "all") {
        get {
          complete {
            "Morning, guest. apiVersion = " + apiVersion + ", brandCode =" + brandCode
          }
        }
      } ~
        path("resource" / "admin-only") {
          authenticate(adminOnly) { user =>
            get {
              complete {
                "Morning, " + user.userName + ". apiVersion = " + apiVersion + ", brandCode =" + brandCode
              }
            }
          }
        } ~
        path("resource" / "customer-only") {
          authenticate(customerOnly) { user =>
            get {
              complete {
                "Morning, " + user.userName + ". apiVersion = " + apiVersion + ", brandCode =" + brandCode
              }
            }
          }
        } ~
        path("resource" / "better-admin") {
          authenticate(withRole("manager")) { user =>
            get {
              complete {
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