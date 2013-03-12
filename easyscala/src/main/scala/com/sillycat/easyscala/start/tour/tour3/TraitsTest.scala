package com.sillycat.easyscala.start.tour.tour3

class Point(xc:Int, yc: Int) extends Similarity{
  var x: Int = xc
  var y: Int = yc
  def isSimilar(obj:Any):Boolean = {
    obj.isInstanceOf[Point] &&
    obj.asInstanceOf[Point].x == x
  }
}

object TraitsTest extends App{
	val p1 = new Point(2,3)
	val p2 = new Point(2,4)
	val p3 = new Point(3,3)
	
	println(p1.isNotSimilar(p2)) //false
	println(p1.isNotSimilar(p3)) // true
	println(p1.isNotSimilar(3))  //true
}