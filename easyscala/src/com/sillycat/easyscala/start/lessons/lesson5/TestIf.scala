package com.sillycat.easyscala.start.lessons.lesson5

object TestIf {

  def main(args: Array[String]): Unit = {
        var filename = "default.txt"
        println(args.isEmpty)
        if (!args.isEmpty){
            filename = args(0)
        }
        println("filename: " + filename)
 
        //if statement will return the last sentence
        val filename2 =
            if (args.isEmpty) "default.txt" else args(0)
        println ("filename2: " + filename2)
    }
}