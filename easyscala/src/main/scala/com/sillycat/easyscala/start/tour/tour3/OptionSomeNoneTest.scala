package com.sillycat.easyscala.start.tour.tour3

object OptionSomeNoneTest {

  def toInt(in: String): Option[Int] = {
    try {
      Some(Integer.parseInt(in.trim()))
    } catch {
      case e: NumberFormatException => None
    }
  }

  def main(args: Array[String]): Unit = {
    toInt("100") match {
      case Some(i) => println(i)
      case None => println("That didn't work.")
    } // 100
    
    toInt("hello") match {
      case Some(i) => println(i)
      case None => println("That didn't work.")
    } //That didn't work.
    
    val bag = List("1", "2", "foo", "3", "bar")
    val sum = bag.flatMap(toInt).sum  //flatMap know how to handle with None, Option, Some
    println(sum)  //6
    
    
  }

}