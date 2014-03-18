package com.sillycat.easynosqlscala.app

import redis.clients.jedis.{JedisCluster, HostAndPort, Jedis}
import java.util.HashSet

object TestRedisDBConnectionApp extends App{
  val jedis = new Jedis("localhost")
  jedis.set("name","sillycat")
  println(jedis.get("name"))

//  val jedisClusterNodes = new HashSet[HostAndPort]
//  jedisClusterNodes.add(new HostAndPort("127.0.0.1", 6379))
//  jedisClusterNodes.add(new HostAndPort("127.0.0.1", 6479))
//  val jc = new JedisCluster(jedisClusterNodes)
//  jc.set("age","32")
//  println("name = " + jedis.get("name") + " age = " + jc.get("age"))

}
