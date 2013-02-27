package com.sillycat.easyscala.start.lessons.lesson4

class Rational(n: Int, d: Int) {
  // check the parameter
  require(d != 0, "d can not be 0")

  private val g = gcd(n, d)

  // default is public
  val numer = n / g
  val denom = d / g

  def this(n: Int) = this(n, 1)

  //Override a method
  override def toString = n + "/" + d

  def ＋(that: Rational): Rational = {
    new Rational(n * that.denom + that.numer * d,
      d * that.denom)
  }

  def <(that: Rational): Boolean = {
    numer * that.denom < denom * that.numer
  }

  def max(that: Rational): Rational = {
    if (this < that) that else this
  }

  // greatest common divisor
  private def gcd(a: Int, b: Int): Int = {
    if (b == 0) a else gcd(b, a % b)
  }
  
}