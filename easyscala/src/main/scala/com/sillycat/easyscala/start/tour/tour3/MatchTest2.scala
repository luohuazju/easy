package com.sillycat.easyscala.start.tour.tour3

object MatchTest2 extends App {
  def matchTest(x: Any): Any = x match {
    case 1 => "one"
    case "two" => 2
    case y: Int => "scala.Int"
  }
  println(matchTest("two")) //2
  println(matchTest(2))     //scala.Int
  println(matchTest(1))		//one
}