package com.sillycat.easyscala

object TestMatch {

  def main(args: Array[String]): Unit = {
    val firstArg = if (!args.isEmpty) args(0) else ""
    var n = 10000
    var friend = "adsfadfasf"
    firstArg match {
      case "salt" => 
        friend = "pepper"
         n = 100
      case "chips" => 
        friend = "salsa"
        n = 10
      case "eggs" => 
        friend = "bacon"
        n = 1
      case _ => 
        friend = "huh?"
        n = 0
    }
    println(friend + n.toString());
  }

}