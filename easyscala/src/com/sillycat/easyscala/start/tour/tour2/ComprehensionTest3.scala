package com.sillycat.easyscala.start.tour.tour2

object ComprehensionTest3 extends App {
  for (i <- Iterator.range(0, 20);
       j <- Iterator.range(i + 1, 20) if i + j == 32)
  println("(" + i + ", " + j + ")")
}