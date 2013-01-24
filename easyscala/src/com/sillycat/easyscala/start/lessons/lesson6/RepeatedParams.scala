package com.sillycat.easyscala.start.lessons.lesson6

object RepeatedParams {
  def main(args: Array[String]): Unit = {
    echo()
    echo("Hello")
    echo("Hello", "World")

    val a = Array("Hello", "world")
    // take all the elements in a to parameters pass to echo
    echo(a: _*)
  }

  // The parameters are multiple and changeable
  def echo(args: String*): Unit = {
    for (arg <- args)
      println(arg)
  }
}