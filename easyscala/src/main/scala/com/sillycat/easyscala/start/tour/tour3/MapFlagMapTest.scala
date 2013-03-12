package com.sillycat.easyscala.start.tour.tour3

object MapFlagMapTest {

  def main(args: Array[String]): Unit = {
    val l1 = List(1,2,3,4,5)
    var l2 = l1.map(x => x*2)
    println(l1 + " " + l2)
    var l3 = l1.map(x => f(x))
    println(l1 + " " + l3)
    
    var l4 = l1.map(x => g(x))
    //List(1, 2, 3, 4, 5) 
    //List(List(0, 1, 2), List(1, 2, 3), List(2, 3, 4), List(3, 4, 5), List(4, 5, 6))
    println (l1 + " " + l4)
    var l5 = l1.flatMap(x=>g(x))
    //List(1, 2, 3, 4, 5) 
    //List(0, 1, 2, 1, 2, 3, 2, 3, 4, 3, 4, 5, 4, 5, 6)
    println (l1 + " " + l5)
    
    var l6 = l3.flatMap(x => x )
    println (l3 + " " + l6) 
    //List(None,None,Some(3),Some(4),Some(5)) List(3,4,5)
  }
  
  def f(x: Int) = if(x > 2) Some(x) else None
  
  def g(v:Int) = List(v-1, v, v+1)

}