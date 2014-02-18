package com.sillycat.easycassandraserver.apps

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.exceptions.AlreadyExistsException
import com.datastax.driver.core._
import org.slf4j.LoggerFactory
import scala.collection.JavaConversions._

object CassandraDataStaxSchemaApp extends App{

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

  //insert
  val insertSQL = session.prepare(
    """
      | insert into books ( brandCode, deviceId, unixtime, notes) values ( ?, ?, ?, ? )
    """.stripMargin)
  session.execute(insertSQL.bind("sillycat","iphone5", 1L:java.lang.Long, "There is a book there."))
  session.execute(insertSQL.bind("sillycat","iphone5", 2L:java.lang.Long, "I update the os to 6.0"))
  session.execute(insertSQL.bind("sillycat","iphone5", 3L:java.lang.Long, "I update the os to 7.0"))
  session.execute(insertSQL.bind("sillycat","android", 1L:java.lang.Long, "I update the os 2.1"))
  session.execute(insertSQL.bind("sillycat","itouch", 2L:java.lang.Long, "I update the os 2.2"))

  cluster.shutdown()
}
