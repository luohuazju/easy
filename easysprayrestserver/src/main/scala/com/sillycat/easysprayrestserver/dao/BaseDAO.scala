package com.sillycat.easysprayrestserver.dao

import scala.slick.driver.ExtendedProfile
import scala.slick.driver.H2Driver
import scala.slick.driver.MySQLDriver
import scala.slick.session.Database
import scala.slick.session.Database.threadLocalSession
import scala.slick.session.Session

import com.sillycat.easysprayrestserver.util.DBConn
import com.sillycat.easysprayrestserver.util.TestDBConn

class BaseDAO(override val profile: ExtendedProfile, dbConn: DBConn) extends ProductDAO with UserDAO with Profile {

  def db: Database = { dbConn.database }

  def create: Unit = db withSession {
    Products.create
    Users.create
  }

  def drop: Unit = db withSession {
    Products.drop
    Users.create
  }

  def doWithSession(f: Unit => Unit) = db withSession { f }

}

object BaseDAO {
  import com.sillycat.easysprayrestserver.model.DBType._
  def apply: BaseDAO = {
    new BaseDAO(MySQLDriver, DBConn)
  }

  def apply(s: String): BaseDAO = s match {
    case "test" => new BaseDAO(H2Driver, TestDBConn)
    case _ => new BaseDAO(MySQLDriver, DBConn)
  }

  // Clients can import this rather than depending on slick directly
  implicit def threadLocalSession: Session = scala.slick.session.Database.threadLocalSession
}
