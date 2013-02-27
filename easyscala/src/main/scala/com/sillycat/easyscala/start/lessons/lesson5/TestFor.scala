package com.sillycat.easyscala.start.lessons.lesson5

import scala.Array.canBuildFrom

object TestFor {

  def main(args: Array[String]): Unit = {
    val files = (new java.io.File(".")).listFiles()
    println("==========file names1 :")
    for (file <- files) {
      print(file + " ")
    }
    println();
    println("==================================");

    println("==========file names2 :")
    for (f <- files) print(f + " ")
    println();
    println("====================================");
    
    // 1 to 5 will return Range(1, 6)
    // 1, 2, 3, 4, 5
    println("==========numbers 1 :")
    for (i <- 1 to 5) print(i + " ")
    println();
    println("=======================================");
    
    // 0 until 5 will return Range(0, 5)
    // 0, 1, 2, 3, 4
    println("===========numbers 2: ");
    for (i <- 0 until 5) print(i + " ")
    println();
    println("============================");

    // multiple checkpoint
    for (
      file <- files if (file.getName.endsWith("bin"))
    ) println(file)

    // for
    for (
      file <- files 
      if (file.isFile) 
      if (file.getName.endsWith("gitignore"))
    ) println(file)

    //put all the matched files in array
    val sfiles = for (
      file <- files if (file.isFile)
    ) yield file

    // files in array
    for (i <- 0 to sfiles.length - 1){
      print(sfiles(i))
    }


  }

}