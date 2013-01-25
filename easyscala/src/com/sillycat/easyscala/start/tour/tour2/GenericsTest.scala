package com.sillycat.easyscala.start.tour.tour2

class Stack[T] {
  var elems: List[T] = Nil
  def push(x: T) { elems = x :: elems }
  def top: T = elems.head
  def pop() { elems = elems.tail }
}

object GenericsTest extends App {
  val stack = new Stack[Int]
  stack.push(1)
  stack.push('a')
  println(stack.top) // 97
  stack.pop()
  println(stack.top) // 1
}