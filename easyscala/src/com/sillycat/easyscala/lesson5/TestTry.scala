package com.sillycat.easyscala.lesson5
import java.io.FileReader

object TestTry {

  def main(args: Array[String]): Unit = {
    var f = new FileReader("test.txt")
    try {
      // use the file
    } catch {
      case ex: java.io.FileNotFoundException =>
        println(ex)
      case ex: java.io.IOException =>
        println(ex)
    } finally {
      f.close()
    }
  }

}