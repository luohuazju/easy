package com.sillycat.easyscala

class Rational(n: Int, d: Int) {
  // 检查输入参数
  require(d != 0, "d can not be 0")

  // 定义私有成员
  private val g = gcd(n, d)

  // 定义成员变量，默认为public，外部可以访问
  val numer = n / g
  val denom = d / g

  // 定义其它构造函数
  def this(n: Int) = this(n, 1)

  override def toString = n + "/" + d

  def ＋(that: Rational): Rational = {
    new Rational(this.numer * that.denom
      + that.numer * this.denom,
      this.denom * that.denom)

    // this可以忽略
    new Rational(numer * that.denom + that.numer * denom,
      denom * that.denom)

    // numer可以使用n代替，denom可以使用d代替
    // 但是that.denom不能用that.d代替，
    // that.number不能用that.n代替
    // 不知道scala为什么设计成这样
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
  // 最大因子
  private def gcd(a: Int, b: Int): Int = {
    if (b == 0) a else gcd(b, a % b)
  }
}