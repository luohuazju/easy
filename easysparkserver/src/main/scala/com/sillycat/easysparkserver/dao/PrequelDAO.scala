package com.sillycat.easysparkserver.dao


import com.typesafe.config._
import net.noerd.prequel.{IsolationLevels, SQLFormatter, DatabaseConfig}
import com.sillycat.easysparkserver.model.ProductsPrequel

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/24/13
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
object PrequelDAO extends ProductsPrequel{

  val config = ConfigFactory.load()

  implicit val testDatabase: DatabaseConfig = DatabaseConfig(
    driver = config.getString("database.driver"),
    jdbcURL = config.getString("database.url"),
    username = config.getString("database.username"),
    password = config.getString("database.password"),
    sqlFormatter = SQLFormatter.HSQLDBSQLFormatter,
    isolationLevel = IsolationLevels.RepeatableRead
    //driver = "org.hsqldb.jdbc.JDBCDriver",
    //jdbcURL = "jdbc:hsqldb:mem:testmemdb"
    //There are some other configuration like driver, jdbcURL, username, password, isolationLevel,
    //sqlFormatter, poolConfig
    //to know the detail, just read the source codes in net.noerd.prequel.DatabaseConfig
  )

  def create: Unit = {
      Products.create
  }

  def drop: Unit = {
    Products.drop
  }

}
