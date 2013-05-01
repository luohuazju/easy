package com.sillycat.easysprayrestserver.actor

import akka.actor.{ Props, Actor }
import spray.routing._
import spray.routing.directives._
import spray.util.LoggingContext
import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport._
import shapeless._

class URLRouterActor extends Actor with URLRouterService {
  def actorRefFactory = context
  def receive = runRoute(route)
}

trait URLRouterService extends HttpService {
  def route = {
    pathPrefix(Version / BrandCode) { (apiVersion, brandCode) =>

      path("resource" / "all") {
        get {
          complete {
            "Morning, guest. apiVersion = " + apiVersion + ", brandCode ="  + brandCode
          }
        }
      } ~
        path("resource" / "admin-only") {
          get {
            complete {
              "Morning, admin-only. apiVersion = " + apiVersion + ", brandCode ="  + brandCode
            }
          }
        } ~
        path("resource" / "customer-only") {
          get {
            complete {
              "Morning, customer-only. apiVersion = " + apiVersion + ", brandCode ="  + brandCode
            }
          }
        } ~
        path("resource" / "better-admin") {
          get {
            complete {
              "Morning, better-admin. apiVersion = " + apiVersion + ", brandCode ="  + brandCode
            }
          }
        } ~
        path("resource" / "better-customer") {
          get {
            complete {
              "Morning, better-customer. apiVersion = " + apiVersion + ", brandCode ="  + brandCode
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