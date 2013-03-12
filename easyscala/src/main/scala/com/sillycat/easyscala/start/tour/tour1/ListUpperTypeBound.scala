package com.sillycat.easyscala.start.tour.tour1

trait Similar {
  def isSimilar(x: Any): Boolean
}

case class MyInt(x: Int) extends Similar {
  def isSimilar(m: Any): Boolean = {
    m.isInstanceOf[MyInt] && m.asInstanceOf[MyInt].x == x
  }
}

object ListUpperTypeBound {

  def findSimilar[T <: Similar](e: T, xs: List[T]): Boolean = {
    if (xs.isEmpty) false
    else if (e.isSimilar(xs.head)) true
    else findSimilar[T](e, xs.tail)
  }

  def main(args: Array[String]): Unit = {
    var l1 = List(1, 3, 5, 7)
    println(l1.head + " " + l1.tail) //1 List(3,5,7)

    val list: List[MyInt] = List(MyInt(1), MyInt(2), MyInt(3))
    println(findSimilar[MyInt](MyInt(4), list))  //false
    println(findSimilar[MyInt](MyInt(2), list))  //true
  }

}