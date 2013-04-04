package com.sillycat.easyscala.start.lessons.lesson3

object EverythingIsObject {

  def main(args:Array[String]): Unit ={
    //create an array
    var ar = Array("Hello", ",", "World")
    
    //0 is object, to method for 0, return Range object
    for (i <- 0 to 2){
      println(ar(i))
    }
    
    println(ar.contains(","))
  }
  
}