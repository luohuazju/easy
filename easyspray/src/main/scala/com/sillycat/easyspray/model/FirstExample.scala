package com.sillycat.easyspray.model

// Use H2Driver to connect to an H2 database
import scala.slick.driver.H2Driver.simple._
// Use the implicit threadLocalSession
import Database.threadLocalSession

object FirstExample extends App {

  object Suppliers extends Table[(Int, String, String, String, String, String)]("SUPPLIERS") {
    def id = column[Int]("SUP_ID", O.PrimaryKey) // 1 This is the primary key column   
    def name = column[String]("SUP_NAME") // 2
    def street = column[String]("STREET") //3
    def city = column[String]("CITY") //4
    def state = column[String]("STATE") //5
    def zip = column[String]("ZIP") // 6
    def * = id ~ name ~ street ~ city ~ state ~ zip
  }

  object Coffees extends Table[(String, Int, Double, Int, Int)]("COFFEES") {
    def name = column[String]("COF_NAME", O.PrimaryKey) //1
    def supID = column[Int]("SUP_ID") //2
    def price = column[Double]("PRICE") //3
    def sales = column[Int]("SALES") //4
    def total = column[Int]("TOTAL") //5
    def * = name ~ supID ~ price ~ sales ~ total
    def supplier = foreignKey("SUP_FK", supID, Suppliers)(_.id)
  }

  val ddl = Suppliers.ddl ++ Coffees.ddl
  
  Database.forURL("jdbc:h2:mem:test1", driver = "org.h2.Driver") withSession {
    
    ddl.create
    

    Suppliers.insert(1, "Acme, Inc.", "99 Market Street", "Groundsville", "CA", "95199")
    Suppliers.insert(2, "Superior Coffee", "1 Party Place", "Mendocino", "CA", "95460")
    Suppliers.insert(3, "The High Ground", "100 Coffee Lane", "Meadows", "CA", "93966")

    Coffees.insertAll(
      ("Colombian", 1, 7.99, 0, 0),
      ("French_Roast", 3, 8.99, 0, 0),
      ("Espresso", 2, 9.99, 0, 0),
      ("Colombian_Decaf", 2, 8.99, 0, 0),
      ("French_Roast_Decaf", 1, 9.99, 0, 0))

    //println("Coffees:")
    Query(Coffees) foreach {
      case (name, supID, price, sales, total) =>
      //println("  " + name + "\t" + supID + "\t" + price + "\t" + sales + "\t" + total)
    }

    //println("Manual join:")
    val q2 = for {
      c <- Coffees if c.price < 9.0 //table 1 with price condition
      s <- Suppliers if s.id === c.supID //table 2 with join id
    } yield (c.name, s.name)
    //for (t <- q2) println("  " + t._1 + " supplied by " + t._2)

    //println("Join by foreign key:")
    val q3 = for {
      c <- Coffees if c.price < 9.0
      s <- c.supplier
    } yield (c.name, s.name)
    // This time we read the result set into a List
    val l3: List[(String, String)] = q3.list
    //for ((s1, s2) <- l3) println("  " + s1 + " supplied by " + s2)

    //println(q3.selectStatement)

    println("Coffees per supplier:")
    val q4 = (for {
      c <- Coffees
      s <- c.supplier
    } yield (c, s)).groupBy(_._2.id).map {
      case (_, q) => (q.map(_._2.name).min.get, q.length)
    }
    // .get is needed because SLICK cannot enforce statically that
    // the supplier is always available (being a non-nullable foreign key),
    // thus wrapping it in an Option
    q4 foreach {
      case (name, count) =>
        println("  " + name + ": " + count)
    }
    
    ddl.drop
    
    ddl.createStatements.foreach(println)
    ddl.dropStatements.foreach(println)
  }

}