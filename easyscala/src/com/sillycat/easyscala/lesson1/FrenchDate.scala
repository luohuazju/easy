package com.sillycat.easyscala.lesson1
import java.text.DateFormat
import java.util.Date
import java.util.Locale

object FrenchDate {

  def main(args: Array[String]): Unit = {
    val now = new Date
    val df = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE)
    println(df format now)

    print(1 + 2 * 3)
  }

}