package com.sillycat.easyscala.start.lessons.lesson1

import com.sillycat.easyscala.start.lessons.lesson1.Complex

object ComplexNumbers {
  def main(args:Array[String]) {
    val c= new Complex(1.3,3.4)
    println("imaginary part:" + c.im)
    println(c)
  }
}