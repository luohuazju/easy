package com.sillycat.easydisruptor

import java.util.concurrent.Executors

import com.lmax.disruptor.{WorkHandler, SleepingWaitStrategy}
import com.lmax.disruptor.dsl.{ProducerType, Disruptor}
import com.sillycat.easydisruptor.consumer._
import com.sillycat.easydisruptor.factory.LongEventFactory
import com.sillycat.easydisruptor.model.{Message, LongEvent}
import com.sillycat.easydisruptor.producer.LongEventProducer

/**
 * Created by carl on 8/7/14.
 */
object MultiHandlerEventApp extends App {

  val numberOfWorkers = 10

  val executor = Executors.newCachedThreadPool()
  val factory = new LongEventFactory()

  // Specify the size of the ring buffer, must be power of 2.
  //1024*4 = 110 seconds
  //1024   = 112 seconds
  val bufferSize = 1024


  val disruptor = new Disruptor[LongEvent](factory, bufferSize, executor, ProducerType.SINGLE, new SleepingWaitStrategy())
  //set consumer/handler
  val logWorkHandler = new Array[WorkHandler[LongEvent]](numberOfWorkers)
  for(a <- 0 to numberOfWorkers - 1){
    logWorkHandler(a) = new LongEventWorkHandler
  }

  val gcmWorkHandler = new Array[WorkHandler[LongEvent]](numberOfWorkers)
  for(a <- 0 to numberOfWorkers - 1){
    gcmWorkHandler(a) = new GCMWorkHandler
  }

  val apnsWorkHandler = new Array[WorkHandler[LongEvent]](numberOfWorkers)
  for(a <- 0 to numberOfWorkers - 1){
    apnsWorkHandler(a) = new APNSWorkHandler
  }

  disruptor.handleEventsWithWorkerPool(logWorkHandler:_*)
    .thenHandleEventsWithWorkerPool(apnsWorkHandler:_*)
    .thenHandleEventsWithWorkerPool(gcmWorkHandler: _*)

  val ringBuffer = disruptor.start()
  //fetch the ringBuffer, then producer can use it

  val producer = new LongEventProducer(ringBuffer)

  val beginTime = System.currentTimeMillis()
  for (a <- 1 to 2000) {
    val message = Message(beginTime, a)
    producer.onData(message)
  }
}
