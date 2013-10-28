package com.sillycat.easycassandraserver.apps

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 7/11/13
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */
object Function1App extends App{

  def filter1(): Function1[String, Boolean] = {
    val set1 = Set("1","2","3")
    //Some(set1).getOrElse((deviceId: String) => true)
    None.getOrElse((deviceId: String) => true)
  }

  val filterFuction1 = filter1()

  println(filterFuction1)

  println(filterFuction1("1"))
  println(filterFuction1("2"))
  println(filterFuction1("5"))

  val filter2 = Set("1","2","3")

  filter2 match {
    case x: Seq => x
    case _ => good
  }

  println(filter2)

  println(filter2("1"))
  println(filter2("5"))

  val filter3 = List("1","2","3")

  println("filter3= " + filter3)

  println(filter3("1"))
  println(filter3("5"))

}
