package com.sillycat.easyspray.caching

import scala.concurrent.Future

import akka.actor.ActorSystem
import spray.caching.Cache
import spray.caching.LruCache
import spray.util._

object CachingTest extends App {
  implicit val system = ActorSystem()

  // if we have an "expensive" operation
  def expensiveOp(): Double = new util.Random().nextDouble()

  // and a Cache for its result type
  val cache: Cache[Double] = LruCache()

  // we can wrap the operation with caching support
  // (providing a caching key)
  def cachedOp[T](key: T): Future[Double] = cache(key) {
    expensiveOp()
  }

  // and profit
//  cachedOp("foo").await === cachedOp("foo").await
//  cachedOp("bar").await !== cachedOp("foo").await
  cachedOp("foo").await == cachedOp("foo").await
  cachedOp("bar").await != cachedOp("foo").await

}