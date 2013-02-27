package com.sillycat.easyscala.start.lessons.lesson2

object Loop {

  def main(args:Array[String]) : Unit = {
    //loop like java
    var i = 0
    while (i< args.length ){
      println(args(i))
      i = i + 1
    }
    
    for(arg <- args)
      println(arg)
      
    args.foreach(arg => println(arg))
    
    args.foreach(println)
  }
  
}