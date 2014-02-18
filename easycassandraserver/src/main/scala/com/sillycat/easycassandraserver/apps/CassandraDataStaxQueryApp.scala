package com.sillycat.easycassandraserver.apps

import org.slf4j.LoggerFactory
import com.datastax.driver.core.{Session, SocketOptions, Cluster}
import com.datastax.driver.core.exceptions.AlreadyExistsException
import scala.collection.JavaConversions._

object CassandraDataStaxQueryApp extends App{

  val log = LoggerFactory.getLogger("CassandraConfig")

  val hosts = "127.0.0.1,127.0.0.2,127.0.0.3"
  val nativePort = 9042  //9042, 9160

  val keyspaceName = "books"
  val columnFamilyName = "books"
  val replicationStrategy = "SimpleStrategy"
  val replicationFactor = 2

  val compactionStrategy = "LeveledCompactionStrategy"

  val keyspaceCql = s"""
         create keyspace $keyspaceName
         with replication = { 'class': '$replicationStrategy', 'replication_factor': $replicationFactor }
         """

  val tableCql = s"""
    create table $columnFamilyName (
      brandCode text,
      deviceId text,
      unixtime bigint,
      notes text,
      primary key ((brandCode, deviceId), unixtime)
    ) with compact storage
      and compression = { 'sstable_compression' : '' }
      and compaction = { 'class' : '$compactionStrategy', 'sstable_size_in_mb' : 10 }
      and clustering order by (unixtime desc)
        """

  lazy val cluster: Cluster = {
    val b = Cluster.builder().
      addContactPoints(hosts.split(","): _*).
      withPort(nativePort)
    val option = new SocketOptions()
    option.setReadTimeoutMillis(12000)
    b.withSocketOptions(option)
    val c = b.build()
    val s = c.connect()
    try {
      s.execute(keyspaceCql)
    } catch {
      case x: AlreadyExistsException => log.info(x.getMessage)
    }
    c
  }

  lazy val session: Session = {
    val s = cluster.connect(keyspaceName)
    try {
      s.execute(tableCql)
    } catch {
      case x: AlreadyExistsException => log.info(x.getMessage)
    }
    s
  }

  //query1
  val querySQL1 = session.prepare("""
      select notes from books where brandCode = ? and deviceId = ?
      limit 1
      """)
  val result1 = session.execute(querySQL1.bind("sillycat", "iphone5"))
  val resultString1 = result1.one().getString("notes")
  println("resultString1 = " + resultString1)

  //query2
  val querySQL2 = session.prepare("""
      select notes from books where brandCode = ? and deviceId = ?
                                  """)
  val result2 = session.execute(querySQL2.bind("sillycat", "iphone5"))

  result2.all().foreach{ row=>
    println("resultString2 = " + row.getString("notes"))
  }

  cluster.shutdown()
}
