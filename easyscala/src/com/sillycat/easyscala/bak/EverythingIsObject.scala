package com.sillycat.easyscala

object EverythingIsObject {

  def main(args:Array[String]): Unit ={
    //create a array
    var ar = Array("Hello", ",", "World")
    //0 is object, to method for 0, return Range object
    for (i <- 0 to 2){
      println(ar(i))
    }
    
    println(ar.contains(","))
  }
  
}