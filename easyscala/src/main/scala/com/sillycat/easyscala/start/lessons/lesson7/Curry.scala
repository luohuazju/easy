package com.sillycat.easyscala.start.lessons.lesson7

object Curry {
  def main(args: Array[String]): Unit = {
    //take 2 group of parameters
    val v = sum(3)(4)
    println(v) //7

    //make the first parameter constant
    val s1 = sum(1) _
    val v1 = s1(3)
    println(v1) //4, equal to sum(1)(3)

    //make the first parameter constant
    val s2 = sum3(1) _
    val v2 = s2(2)(3)
    println(v2)   //6

    // make the first and second parameter constant
    val s3 = sum3(1)(2) _
    val v3 = s3(3)
    println(v3) // 6

    //s2=sum3(1)_
    val s4 = s2(2)   // s4 = sum3(1)(2)
    val v4 = s4(3)   // v4 = sum3(1)(2)(3)
    println(v4)    //6

    val v5 = sum2(1)(2, 3)   //6

    val s6 = sum2(1) _
    val v6 = s6(2, 3)    //sum2(1)(2,3) 6
  }

  def sum(x: Int)(y: Int) = x + y

  def sum3(x: Int)(y: Int)(z: Int) = x + y + z

  def sum2(x: Int)(y: Int, z: Int) = x + y + z
}