package com.sillycat.easyscala.lesson7
import java.io.FileReader

object SafeFile {
  def main(args: Array[String]): Unit = {
    safeFileReaderOp("./readme.md", print)
  }

  //Function passed to the upper function
  def print(reader: FileReader) = {
    val i = reader.read
    println(i.toChar)
  }

  def safeFileReaderOp(filename: String,
    op: FileReader => Unit) = {
    val reader = new FileReader(filename)
    try {
      op(reader)
    } finally {
      reader.close()
    }
  }
}