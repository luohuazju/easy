package com.sillycat.easyscala.lesson6

object TestClosure {

  def main(args: Array[String]): Unit = {
    var more = 0
    //in function, we can use the variable defined outside the function
    val mf = (_: Int) + more
    println(mf(10))

    //the variable can be changed
    more = 10
    println(mf(10))

    val l = List(1, 3, 4, 5, 9)
    var sum = 0
    l.foreach(sum += _)
    println(sum)
  }

}