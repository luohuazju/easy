package com.sillycat.easyscala.start.tour.tour3

class Decorator(left: String, right: String) {
  def layout[A](x: A) = left + x.toString() + right
}

object FunTest extends App {
  //defined the function, take other function and values as 
  //parameters
  def apply(f: Int => String, v: Int) = f(v)
  val decorator = new Decorator("[", "]")
  println(apply(decorator.layout, 7))  //[7]
}