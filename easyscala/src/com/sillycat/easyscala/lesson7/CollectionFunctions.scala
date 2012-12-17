package com.sillycat.easyscala.lesson7

object CollectionFunctions {
  def main(args: Array[String]): Unit = {
    val l = List(1, 3, 5, 7, -1)

    // check the elements greater than 0
    val hasNeg = l.exists((n: Int) => n > 0)
    println("has neg " + hasNeg) //has neg true

    // check if there is odd element
    val hasOdd = l.exists(_ % 2 == 0)
    println("has odd " + hasOdd) //has odd false

    // count the elements greater than 2
    val largeThen2 = l.count(_ > 2)
    println("count of > 2 = " + largeThen2) //count of > 2 = 3

    // copy the elements greater than 0 to another newly list
    val pos = l.filter(_ > 0)
    println("all positive " + pos) //List(1, 3, 5, 7)

    // check if all the elements are greater than 0 
    val allPos = l.forall(_ > 0)
    println("is all positive " + allPos) //is all positive false

    // just print every element
    l.foreach(print _)  //1357-1
    println

    // sum all the element
    var sum = 0
    l.foreach(sum += _)
    println("sum is " + sum) //sum is 15

    //square all the elements
    val square = l.map((n: Int) => n * n)
    println("squared list " + square) //List(1, 9, 25, 49, 1)

    // reverse all the element
    val reverse = l.reverse
    println("reversed list " + reverse) //List(-1, 7, 5, 3, 1)

    // sort
    val sort = l.sortWith((a, b) => a > b)
    println("sorted list " + sort) //(7, 5, 3, 1, -1)
  }
}