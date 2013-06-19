package com.sillycat.easycassandraserver.apps

import me.prettyprint.hector.api.factory.HFactory
import me.prettyprint.cassandra.model.{BasicColumnFamilyDefinition, BasicColumnDefinition}
import me.prettyprint.hector.api.ddl.{ColumnFamilyDefinition, KeyspaceDefinition, ComparatorType, ColumnIndexType}
import me.prettyprint.cassandra.serializers.StringSerializer
import java.util.Arrays
import me.prettyprint.cassandra.service.ThriftCfDef
import me.prettyprint.hector.api.Keyspace
import me.prettyprint.hector.api.mutation.Mutator


/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/18/13
 * Time: 1:28 PM
 * To change this template use File | Settings | File Templates.
 */
object DemoApp extends App{

  val stringSerializer = StringSerializer.get();

  //connect to the cassandra server
  val cluster = HFactory.getOrCreateCluster("Test-Cluster","localhost:9160")

  //drop key space
  if ( cluster.describeKeyspace("DEMO") != null ) {
    cluster.dropKeyspace("DEMO")
  }

  //column nickname
  val columnNickName = new BasicColumnDefinition();
  columnNickName.setName(stringSerializer.toByteBuffer("nickname"))
  columnNickName.setIndexName("nickname_idx")
  columnNickName.setIndexType(ColumnIndexType.KEYS)
  columnNickName.setValidationClass(ComparatorType.UTF8TYPE.getClassName())

  //column password
  val columnPassword = new BasicColumnDefinition();
  columnPassword.setName(stringSerializer.toByteBuffer("password"))
  columnPassword.setIndexName("password_idx")
  columnPassword.setIndexType(ColumnIndexType.KEYS)
  columnPassword.setValidationClass(ComparatorType.UTF8TYPE.getClassName())

  //row
  val basicColumnFamilyUsers: BasicColumnFamilyDefinition = new BasicColumnFamilyDefinition
  basicColumnFamilyUsers.setKeyspaceName("MEMO")
  basicColumnFamilyUsers.setName("USERS")
  basicColumnFamilyUsers.addColumnDefinition(columnNickName)
  basicColumnFamilyUsers.addColumnDefinition(columnPassword)

  //table
  val columnFamilyUsers: ColumnFamilyDefinition = new ThriftCfDef(basicColumnFamilyUsers);

  //db MEMO
  val keyspaceMemo: KeyspaceDefinition =
    HFactory.createKeyspaceDefinition("MEMO", "org.apache.cassandra.locator.SimpleStrategy",
      1, Arrays.asList(columnFamilyUsers));

  cluster.addKeyspace(keyspaceMemo)


  val keyspaceOperator: Keyspace = HFactory.createKeyspace("MEMO", cluster)

  val mutator:Mutator[String] = HFactory.createMutator(keyspaceOperator, StringSerializer.get());
  mutator.insert("9527", "USERS", HFactory.createStringColumn("NickName", "Sillycat"));

  cluster.getConnectionManager().shutdown();
}
