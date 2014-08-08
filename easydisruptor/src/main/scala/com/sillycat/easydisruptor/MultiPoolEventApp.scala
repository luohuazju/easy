package com.sillycat.easydisruptor

import java.util.concurrent.Executors

import com.lmax.disruptor.SleepingWaitStrategy
import com.lmax.disruptor.dsl.{ProducerType, Disruptor}
import com.sillycat.easydisruptor.consumer.{APNSWorkHandler, GCMWorkHandler}
import com.sillycat.easydisruptor.factory.LongEventFactory
import com.sillycat.easydisruptor.model.{Message, LongEvent}
import com.sillycat.easydisruptor.producer.LongEventProducer
import com.sillycat.easydisruptor.consumer.LongEventWorkHandler
import com.sillycat.easydisruptor.consumer.LongEventHandler

/**
 * Created by carl on 8/7/14.
 */
object MultiPoolEventApp extends App{

  val numberOfWorkers = 10

  val executor = Executors.newCachedThreadPool()

  val factory = new LongEventFactory()

  // Specify the size of the ring buffer, must be power of 2.
  val bufferSize = 1024 * 4


  val disruptor = new Disruptor[LongEvent](factory, bufferSize, executor,ProducerType.SINGLE, new SleepingWaitStrategy())
  //set consumer/handler
  val logWorkHandler = new Array[LongEventWorkHandler](numberOfWorkers)
  for(a <- 0 to numberOfWorkers - 1){
    logWorkHandler(a) = new LongEventWorkHandler
  }

  val logHandler = new LongEventHandler

  val gcmWorkHandler = new Array[GCMWorkHandler](numberOfWorkers)
  for(a <- 0 to numberOfWorkers - 1){
    gcmWorkHandler(a) = new GCMWorkHandler
  }

  val apnsHandler = new Array[APNSWorkHandler](numberOfWorkers)
  for(a <- 0 to numberOfWorkers - 1){
    apnsHandler(a) = new APNSWorkHandler
  }

  disruptor.handleEventsWithWorkerPool(logWorkHandler :_*)
  disruptor.handleEventsWithWorkerPool(gcmWorkHandler :_*)
  disruptor.handleEventsWithWorkerPool(apnsHandler :_*)

  disruptor.start()

  val ringBuffer = disruptor.getRingBuffer()
  val producer = new LongEventProducer(ringBuffer)

  val beginTime = System.currentTimeMillis()
  for( a <- 1 to 2000){
    val message = Message(beginTime,a)
    producer.onData(message)
  }
}