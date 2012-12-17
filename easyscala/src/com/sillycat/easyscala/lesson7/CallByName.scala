package com.sillycat.easyscala.lesson7

object CallByName {
  def main(args: Array[String]): Unit = {
    val o1 = new O()
    val o2 = new O()
    byName(o1 > o2)
    byValue(o1 > o2)
  }

  //a class named O, what an useless name
  class O {
    def >(o: O): Boolean = {
      println("compare")
      true
    }
  }

  // by name parameter, predicate is the name of parameter, actually, it is 
  // predicate
  def byName(predicate: => Boolean) = {
    println("before predicate byName")    //before predicate byName
    predicate                             //compare
    println("after predicate byName")     //after predicate byName
  }

  // by Value 
  def byValue(predicate: Boolean) = {
    println("before predicate byValue")    //compare
    predicate                              //before predicate byValue
    println("after predicate byValue")     //after predicate byValue
  }
}