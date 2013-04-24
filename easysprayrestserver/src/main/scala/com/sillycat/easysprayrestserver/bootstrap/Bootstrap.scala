package com.sillycat.easysprayrestserver.bootstrap

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

trait Bootstrap {
  
  //implicit val system = ActorSystem("RESTAPIService")
  
  val config = ConfigFactory.load()

}