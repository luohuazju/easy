package com.sillycat.easynosqlscala.app

import redis.clients.jedis.{ JedisCluster, HostAndPort, Jedis }
import java.util.HashSet

object TestRedisDBConnectionApp extends App {
  //  val jedis = new Jedis("172.0.0.1", 7000)
  //  jedis.set("name", "sillycat")
  //  println(jedis.get("name"))

  val jedisClusterNodes = new HashSet[HostAndPort]
  jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7000))
  jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7001))
  jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7002))
  jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7003))
  jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7004))
  jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7005))
  jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7006))
  val jc = new JedisCluster(jedisClusterNodes)
  //jc.set("age", "32")
  println("name = " + jc.get("name") + " age = " + jc.get("age"))

}
