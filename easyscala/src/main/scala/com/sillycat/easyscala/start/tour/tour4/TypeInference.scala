package com.sillycat.easyscala.start.tour.tour4

object TypeInference {

  def main(args: Array[String]): Unit = {
    val x = 1 + 2 * 3 // the type is Int
    def succ(x: Int) :Int = x+ 1 // the return type should be :Int and we can ommit it
    
  }

}