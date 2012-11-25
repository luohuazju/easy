package com.sillycat.easyscala.lesson3

object ListIsImmutable {
	def main(args: Array[String]): Unit = {
	  val l1 = List(1,2)
	  val l2 = List(3,4)
	  
	  val l3 = l1:::l2
	  println(l3) //List(1, 2, 3, 4)

	  val l4 = 1::l2
	  println(l4) //List(1, 3, 4)
	  
	  val l5 = 1::2::3::Nil
	  println(l5) //List(1, 2, 3)
	  
	  var l6 = l2.::(13)
	  println(l6)// List(13,3,4)
	  
	  var l7 = 18::l2
	  println(l7)// List(18,3,4)
	}
}