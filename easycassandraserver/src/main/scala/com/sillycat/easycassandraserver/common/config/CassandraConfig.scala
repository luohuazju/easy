package com.sillycat.easycassandraserver.common.config

import com.typesafe.config.ConfigFactory
import me.prettyprint.hector.api.Cluster
import me.prettyprint.hector.api.factory.HFactory
import me.prettyprint.cassandra.service.CassandraHostConfigurator
import me.prettyprint.hector.api.ddl.{ColumnFamilyDefinition, KeyspaceDefinition}
import java.util.{ HashMap => JMap }

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 7/2/13
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */
trait CassandraConfig {
  /**
   * config key for the object .
   * @return
   */
  def configKey: String

  /**
   * key space definition
   * @return
   */
  def keyspaceDefinition: KeyspaceDefinition


  def ksp = HFactory.createKeyspace(keyspaceName, cluster)

  val config = ConfigFactory.load()
  val env = "environment." + config.getString("build.env")

  val keyspaceName = config.getString(path(env, configKey, "keyspaceName"))
  val columnFamilyName = config.getString(path(env, configKey, "columnFamilyName"))
  val replicationFactor = config.getInt(path(env, configKey, "replicationFactor"))
  val replicationStrategy = config.getString(path(env, configKey, "replicationStrategy"))

  val clusterName = config.getString(path(env, "cluster", "name"))
  val host = config.getString(path(env, "cluster", "hostname"))
  val port = config.getString(path(env, "cluster", "port"))

  val cluster: Cluster = HFactory.getOrCreateCluster(clusterName, new CassandraHostConfigurator(host + ":" + port))


  def configCompressionCompaction(cfd: ColumnFamilyDefinition): Unit = {
    val compressionOptions = new JMap[String, String]
    compressionOptions.put("sstable_compression", config.getString(path(env, configKey, "sstable_compression")))
    cfd.setCompressionOptions(compressionOptions)
    cfd.setCompactionStrategy(config.getString(path(env, configKey, "compactionStrategy")))
    val compactionOptions = new JMap[String, String]
    compactionOptions.put("sstable_size_in_mb", config.getString(path(env, configKey, "sstable_size_in_mb")))
    cfd.setCompactionStrategyOptions(compactionOptions)
  }

  cluster.describeKeyspace(keyspaceName) match {
    case null => cluster.addKeyspace(keyspaceDefinition, true)
    case _ => println("Keyspace already exists! " + keyspaceName); cluster
  }

  /**
   * take multiple strings as parameters, connect them with .
   * @param paths
   * @return
   */
  def path(paths: String*) = paths.mkString(".")
}
