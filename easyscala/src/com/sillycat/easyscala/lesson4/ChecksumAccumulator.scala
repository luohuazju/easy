package com.sillycat.easyscala.lesson4

class ChecksumAccumulator {
  //private member in class
  private var sum = 0
  //return void function
  def add(b: Byte): Unit = {
    sum += b
  }
  //return int function, the last statement will be the return value
  def checksum(): Int = {
    return ~(sum & 0xFF) + 1
  }
}