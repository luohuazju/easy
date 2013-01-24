package com.sillycat.easyscala.start.lessons.lesson6

object TestMutableFunction {
  def main(args: Array[String]): Unit = {
    //defined a function x+1
    var increase = (x: Int) => x + 1
    println(increase(10))

    //redefined the function to x+5
    increase = (x: Int) => x + 5
    println(increase(10))

    val l = List(1, 3, 5, 7, 9, 11)
    println(l)

    val l2 = l.filter((x: Int) => x > 2)
    println(l2)

    val l3 = l.filter(x => x > 3)
    println(l3)

    val l4 = l.filter(_ > 5)
    println(l4)

    val f = (_: Int) + (_: Double)
    val v5 = f(3, 5)
    println(v5)

    //sum with multiple parameters
    val asum = sum _
    val v6 = asum(1, 2, 3)
    println(v6)

    val psum = sum(3, _: Int, 5)
    val v7 = psum(7)
    println(v7)
  }

  def sum(a: Int, b: Int, c: Int): Int = {
    a + b + c
  }
}