package com.sillycat.easyscala.start.tour.tour2

object Twice{
  def apply(x:Int): Int = x * 2
  def unapply(z: Int): Option[Int] = if (z%2 == 0) Some(z/2) else None
}

object TwiceTest extends App{
	val x = Twice(21)
	println("apply value: " + x)    //apply value: 42
	x match { case Twice(n) => Console.println("unapply value: " + n) } //unapply value: 21
}