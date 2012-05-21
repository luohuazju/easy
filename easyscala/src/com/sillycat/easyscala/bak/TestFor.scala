package com.sillycat.easyscala

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
    
    // 1 to 5 实际上返回的是一个Range(1, 6)
    // 就是对1,2,3,4,5做循环
    println("==========numbers 1 :")
    for (i <- 1 to 5) print(i + " ")
    println();
    println("=======================================");
    
    // 0 until 5 实际上返回的是一个Range(0, 5)
    // 就是对0, 1,2,3,4做循环
    println("===========numbers 2: ");
    for (i <- 0 until 5) print(i + " ")
    println();
    println("============================");

    // for 里面还可以添加if来过滤
    for (
      file <- files if (file.getName.endsWith("bin"))
    ) println(file)

    // for里面还可以有多个过滤条件
    for (
      file <- files if (file.isFile) if (file.getName.endsWith("gitignore"))
    ) println(file)

    // for循环里找出来的东西还可以放到一个集合里
    val sfiles = for (
      file <- files if (file.isFile)
    ) yield file

    // files in array
    for (i <- 0 to sfiles.length - 1){
      print(sfiles(i))
    }


  }

}