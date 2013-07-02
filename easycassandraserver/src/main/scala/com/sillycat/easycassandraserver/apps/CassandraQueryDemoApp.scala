package com.sillycat.easycassandraserver.apps

import me.prettyprint.hector.api.{Keyspace, Cluster}
import me.prettyprint.hector.api.factory.HFactory
import me.prettyprint.hector.api.query.{MultigetSliceQuery, QueryResult, RangeSlicesQuery}
import scala.Product
import org.joda.time.DateTime
import me.prettyprint.hector.api.beans.{Row, Rows, OrderedRows}
import me.prettyprint.cassandra.serializers.{BigDecimalSerializer, StringSerializer}
import scala.collection.JavaConversions._
import com.sillycat.easycassandraserver.models.Product
import scala.Product

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/21/13
 * Time: 12:46 PM
 * To change this template use File | Settings | File Templates.
 */
object CassandraQueryDemoApp extends App{
  val clusterName = "TestCluster"
  val host = "localhost"
  val port = 9160

  val keyspaceName = "sillycat"
  val columnFamilyName = "products"

  val cluster: Cluster = HFactory.getOrCreateCluster("TestCluster", host + ":" + 9160)

  val p1 = Product(Some(1), "IPHONE5", DateTime.now, 499.99, "iphone5", "China")
  val p2 = Product(Some(2), "IPHONE4", DateTime.now, 199.99, "iphone4", "US")
  val p3 = Product(Some(3), "IPHONE4S", DateTime.now, 299.99, "iphone4s", "TW")

  val keyspaceOperator: Keyspace = HFactory.createKeyspace(keyspaceName, cluster)

  //range query
  val bigDecimalRangeSlicesQuery: RangeSlicesQuery[String, String, java.math.BigDecimal] =
    HFactory.createRangeSlicesQuery(keyspaceOperator, StringSerializer.get(), StringSerializer.get(), BigDecimalSerializer.get())
  bigDecimalRangeSlicesQuery.setColumnFamily(columnFamilyName)
  bigDecimalRangeSlicesQuery.setKeys(p2.productCode, p2.productCode)
  bigDecimalRangeSlicesQuery.setColumnNames("ProductPrice")

  val result1: QueryResult[OrderedRows[String, String, java.math.BigDecimal]] = bigDecimalRangeSlicesQuery.execute()
  val orderedRows1: OrderedRows[String, String, java.math.BigDecimal]= result1.get()

  orderedRows1.toList.foreach( row => println("result1: " + row.getColumnSlice))


  val stringRangeSlicesQuery: RangeSlicesQuery[String, String, String] =
    HFactory.createRangeSlicesQuery(keyspaceOperator, StringSerializer.get(), StringSerializer.get(), StringSerializer.get())
  stringRangeSlicesQuery.setColumnFamily(columnFamilyName)
  stringRangeSlicesQuery.setKeys(p2.productCode, p2.productCode)
  stringRangeSlicesQuery.setColumnNames("ProductName", "Country")

  val result2: QueryResult[OrderedRows[String, String, String]] = stringRangeSlicesQuery.execute()
  val orderedRows2: OrderedRows[String, String, String]= result2.get()

  orderedRows2.toList.foreach( row => println("result2: " + row.getColumnSlice))

//  createIndexedSlicesQuery   time between some date
//  indexedSlicesQuery.addEqualsExpression("birthdate", 1975L);
//  indexedSlicesQuery.addGtExpression("birthmonth", 6L);
//  indexedSlicesQuery.addLtExpression("birthmonth", 8L);
//  indexedSlicesQuery.setColumnNames("birthdate","birthmonth");
//  indexedSlicesQuery.setColumnFamily("Indexed1");
//  indexedSlicesQuery.setStartKey("");

  //multiget
  val multigetSliceQuery: MultigetSliceQuery[String, String, String]  =
    HFactory.createMultigetSliceQuery(keyspaceOperator, StringSerializer.get(), StringSerializer.get(), StringSerializer.get())
  multigetSliceQuery.setColumnFamily(columnFamilyName)
  multigetSliceQuery.setKeys(p1.productCode, p2.productCode, p3.productCode)

  multigetSliceQuery.setRange(null, null, false, 3)  //number 3 is the number of the columns

  println(multigetSliceQuery)
  val result3: QueryResult[Rows[String, String, String]]  = multigetSliceQuery.execute()
  val orderedRows3: Rows[String, String, String] = result3.get()

  orderedRows3.toList.foreach(row => println("result3: " + row.getColumnSlice))

  //pagenation
  val pagenationSliceQuery: RangeSlicesQuery[String, String, String]  =
    HFactory.createRangeSlicesQuery(keyspaceOperator, StringSerializer.get(), StringSerializer.get(), StringSerializer.get())
  pagenationSliceQuery.setColumnFamily(columnFamilyName)
  pagenationSliceQuery.setRange("", "", false, 3)
  pagenationSliceQuery.setRowCount(3)
  val result4: QueryResult[OrderedRows[String, String, String]]  = pagenationSliceQuery.execute()
  val orderedRows4: OrderedRows[String, String, String]  = result4.get()

  orderedRows4.toList.foreach(row => println("result4: " + row.getColumnSlice))

  val lastRow: Row[String,String,String] = orderedRows4.peekLast()
  pagenationSliceQuery.setKeys(lastRow.getKey(), "")
  val orderedRows5 = pagenationSliceQuery.execute().get()

  orderedRows5.toList.foreach(row => println("result5: " + row.getColumnSlice))

  //load all
  val loadAllQuery = HFactory.createRangeSlicesQuery(keyspaceOperator,StringSerializer.get(), StringSerializer.get(), StringSerializer.get())
  loadAllQuery.setColumnFamily(columnFamilyName)
  loadAllQuery.setRange(null, null, false, 1)

  val result6 = loadAllQuery.execute()
  val orderedRows6 = result6.get()

  orderedRows6.toList.foreach{ row =>
    println("result6: " + row.getKey + " " + row.getColumnSlice)

  }
}
