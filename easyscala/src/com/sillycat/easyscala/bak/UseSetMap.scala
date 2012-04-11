package com.sillycat.easyscala
import scala.collection.mutable.HashSet

object UseSetMap {

  def main(args:Array[String]):Unit={
    //default is immutable
    //changes will create new instances
    var s = Set("a",1,"b")
    s += 3
    println(s) //Set(a, 1, b, 3)
    println(s.contains(1)) //true
    println(s("b"))        //true
    println(s.getClass())  //class scala.collection.immutable.Set$Set4
    
    //HashSet need import, and identify mutable/immutable
    var hs = HashSet("b",3) 
    hs.+=(5)
    println(hs)    //Set(b, 3, 5)
    println(hs.contains(3)) //true
    
    var m = Map(1->"a",2->"b","3" ->"c")
    m.+=("4" -> "d")
    println(m)//Map(1 -> a, 2 -> b, 3 -> c, 4 -> d)
    println(m(1) + m("4")) //ad
  }
}