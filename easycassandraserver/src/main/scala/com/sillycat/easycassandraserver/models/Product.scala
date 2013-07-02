package com.sillycat.easycassandraserver.models

import org.joda.time.DateTime
import com.sillycat.easycassandraserver.common.config.CassandraConfig
import me.prettyprint.hector.api.factory.HFactory
import me.prettyprint.hector.api.ddl.ComparatorType
import java.util.Arrays
import me.prettyprint.hector.api.beans.Composite

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/19/13
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
case class Product(id: Option[Long], productName: String, create: DateTime, productPrice: BigDecimal, productCode: String, country: String)

class Products(attrs: Map[String,Any]) {
  override def toString = attrs.toString

  def get(key: String) = Option(attrs.get(key))

  def toBytes = {

  }
}

object Products extends CassandraConfig {

  def configKey = "products"

  def keyspaceDefinition = {
    val cfd = HFactory.createColumnFamilyDefinition(
      keyspaceName,
      columnFamilyName,
      ComparatorType.getByClassName("org.apache.cassandra.db.marshal.ReversedType(org.apache.cassandra.db.marshal.LongType)"))

    cfd.setKeyValidationClass("CompositeType(AsciiType)")
    cfd.setDefaultValidationClass(ComparatorType.BYTESTYPE.getClassName())
    configCompressionCompaction(cfd)

    HFactory.createKeyspaceDefinition(keyspaceName, replicationStrategy, replicationFactor, Arrays.asList(cfd))
  }

  def rowkey(productCode: String): Composite = {
    val rk = new Composite
    rk.add(0, productCode)
    rk
  }

}