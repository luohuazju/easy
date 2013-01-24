package com.sillycat.easyscala.start.lessons.lesson6

object NamedArgument {
  def main(args: Array[String]): Unit = {
    val a = speed(distance = 100, time = 9)
    println(a) //11.1111111111

    //give the parameter names during calling the functions
    val b = speed(time = 10, distance = 101)
    println(b) //10.1
    
    println(speed(1,2)) //0.5
  }

  //define a name for the function
  def speed(distance: Double, time: Double) = distance / time
}