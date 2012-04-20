package com.sillycat.easyscala

object TestMain extends App {
  //println("""Welcome to Ultamix 3000.
  //           Type "HELP" for help.""")

  //    println("""|Welcome to Ultamix 3000.
  //               |Type "HELP" for help.""".stripMargin)
  //               
  //    println("string".indexOf('t'))
  //    println("string" indexOf "g")
  //    println("string".indexOf('s', 0))
  //    
  //    println("string".toUpperCase())
  //    println("string" toUpperCase )
  //    
  //    println("string" == "string") //true

  var r1 = new Rational(1, 3)
  var r3 = new Rational(3, 4)
  println(r1 < r3) //true
  println(r3 < r1) //false
}