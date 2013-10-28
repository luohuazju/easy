package com.sillycat.easynosqlscala.app

import com.mongodb.casbah.Imports._

object TestConnectionApp extends App{
  //connection
  val rs1 = new ServerAddress("localhost", 27017)
  val rs2 = new ServerAddress("localhost", 27018)
  val rs3 = new ServerAddress("localhost", 27019)

  val client = MongoClient(List(rs1, rs2, rs3))

  //database
  val db = client("test")
  println("previous tables = " + db.collectionNames)

  db.dropDatabase()

  //collection
  val colUsers = db("users")

  //create
  val a = MongoDBObject("name" -> "Carl", "nickName" -> "sillycat", "age" -> 31)
  val b = MongoDBObject("name" -> "Rachel", "nickName" -> "kiko", "age" -> 27)

  colUsers.insert(a)
  colUsers.insert(b)

  //count
  println("number of the users = " + colUsers.count() )

  //query
  val all = colUsers.find()
  all.foreach { item =>
    println("item of User = " + item)
  }

  val one = colUsers.findOne(MongoDBObject("name" -> "Carl"))
  println("find one = " + one)

  //update
  colUsers.update(MongoDBObject("name" -> "Carl"),
    MongoDBObject("name" -> "Carl", "nickName" -> "sillycat", "age" -> 31, "language" -> "JAVA"))

  val one_1 = colUsers.findOne(MongoDBObject("name" -> "Carl"))
  println("find one = " + one_1)

  //removing
  colUsers.remove(MongoDBObject("name" -> "Carl"))

  //drop all the data
  colUsers.drop()
}
