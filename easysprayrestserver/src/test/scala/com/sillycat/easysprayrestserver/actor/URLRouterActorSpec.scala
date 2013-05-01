package com.sillycat.easysprayrestserver.actor

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import spray.http._
import StatusCodes._
import spray.http.BasicHttpCredentials
import spray.http.HttpHeaders.Authorization
import spray.routing.AuthenticationFailedRejection
import spray.routing.AuthenticationRequiredRejection
import spray.routing.HttpService
import spray.routing.RequestContext
import spray.routing.authentication.Authentication
import spray.routing.authentication.UserPass
import spray.util.executionContextFromActorRefFactory
import spray.util.pimpSeq

class URLRouterActorSpec extends Specification with Specs2RouteTest with URLRouterService {

  def actorRefFactory = system

  "The URLRouterActor" should {

    "Anyone can visit this page." in {
      Get("/v1/sillycat/resource/all") ~> route ~> check { entityAs[String] must contain("Morning") }
    }

    "Admin can visit this page." in {
      Get("/v1/sillycat/resource/admin-only") ~> addCredentials(BasicHttpCredentials("admin", "admin")) ~> route ~> check { entityAs[String] must contain("Morning") }
    }

    "No UserName Password can not visit this page." in {
      Get("/v1/sillycat/resource/admin-only") ~> route ~> check { 
    	  rejection === AuthenticationRequiredRejection("https", "sillycat")
      }
    }
    
    "Wrong UserName Password can not visit this page." in {
      Get("/v1/sillycat/resource/admin-only") ~> addCredentials(BasicHttpCredentials("admin", "asdfadsf")) ~> route ~> check { 
    	  rejection === AuthenticationFailedRejection("sillycat")
      }
    }

  }

}