package com.sillycat.easyscala.start.tour.tour3

object PolyTest extends App {
  def dup[T](x: T, n: Int): List[T] =
    if (n == 0) {
      Nil
    } else {
      x :: dup(x, n - 1)
    }
  println(dup[Int](3, 4))	//List(3, 3, 3, 3)
  println(dup[String]("three", 3))	//List(three, three, three)
  println(dup("three", 3))
}