package com.sillycat.easyscala.start.lessons.lesson3

object UseTuple {
  
  def main(args: Array[String]) :Unit = {
    val t = (1,2,3)
    println(t._3) //3
    
    println(t) //(1,2,3)
    
    val t2 = (1,"string",2.3)
    println(t2) //(1,string,2.3)
    
    val t3 = ("kiko", "carl", t)
    println(t3) //(kiko,carl,(1,2,3))
  }

}