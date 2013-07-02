package com.sillycat.easycassandraserver.apps

import com.google.common.cache.{Cache, CacheLoader, CacheBuilder, LoadingCache}
import java.util.concurrent.{Callable, TimeUnit}
import org.joda.time.DateTime
import com.sillycat.easycassandraserver.models.Product

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/19/13
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
object GuavaCacheApp extends App{

  //loading cache
  val cacheLoading: LoadingCache[String, Product] = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(5, TimeUnit.SECONDS)
    .build(
    new CacheLoader[String, Product](){
     def load(key: String): Product = {
      val p: Product = Product(None, "Nice Product", DateTime.now, 32.32, "code1", "China")
      p
    }
  })

  val book = cacheLoading.get("key1")
  Console.println("Book 1 hit=" + book)

  Thread.currentThread()
  Thread.sleep(TimeUnit.SECONDS.toMillis(3))
  val book1 = cacheLoading.get("key1")
  Console.println("Book 2 hit=" + book1)

  Thread.currentThread()
  Thread.sleep(TimeUnit.SECONDS.toMillis(6))
  val book2 = cacheLoading.get("key1")
  Console.println("Book 3 hit=" + book2)

  //callable
  val cacheCallable: Cache[String, Product] = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(5, TimeUnit.SECONDS).build()

  val product1 = cacheCallable.get("key2",new Callable[Product](){
    def call: Product = {
       Product(None, "Good Product", DateTime.now, 32.32, "code1", "China")
    }
  })
  Console.println("Product 1 hit=" + product1)

  Thread.currentThread()
  Thread.sleep(TimeUnit.SECONDS.toMillis(3))
  val product2 = cacheCallable.get("key2",new Callable[Product](){
    def call: Product = {
      Product(None, "Good Product", DateTime.now, 32.32, "code1", "China")
    }
  })
  Console.println("Product 2 hit=" + product2)

  Thread.currentThread()
  Thread.sleep(TimeUnit.SECONDS.toMillis(6))
  val product3 = cacheCallable.get("key2",new Callable[Product](){
    def call: Product ={
      Product(None, "Good Product", DateTime.now, 32.32, "code1", "China")
    }
  })
  Console.println("Product 3 hit=" + product3)
}
