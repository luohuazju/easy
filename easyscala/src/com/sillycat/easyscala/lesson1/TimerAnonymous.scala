package com.sillycat.easyscala.lesson1

object TimerAnonymous {

  def oncePerSecond(callback:() => Unit){
    while(true){
      callback();
      Thread sleep 1000
    }
  }
  
  def main(args: Array[String]): Unit = {
    oncePerSecond(()=>println("time goes"))
  }

}