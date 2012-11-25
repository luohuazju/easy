package com.sillycat.easyscala.lesson2

object Love {
  //args parameter name
  //Array[String] parameter type
  //Unit return type, equals to void
  def main(args: Array[String]): Unit = {
    //val is equals to final String exp
    val exp: String = "Happy Valenten's Day"
    val name = "Kiko"
    //String v = ""
    var v = exp + ", " + name
    println(v)
  }

}