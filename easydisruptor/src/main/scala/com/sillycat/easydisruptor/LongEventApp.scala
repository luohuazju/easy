package com.sillycat.easydisruptor

import java.util.concurrent.Executors
import com.lmax.disruptor.SleepingWaitStrategy
import com.sillycat.easydisruptor.factory.LongEventFactory
import com.lmax.disruptor.dsl.{ProducerType, Disruptor}
import com.sillycat.easydisruptor.consumer.{APNSHandler, GCMHandler, LongEventHandler}
import com.sillycat.easydisruptor.producer.LongEventProducer
import com.sillycat.easydisruptor.model.LongEvent
import com.sillycat.easydisruptor.model.Message

/**
 * Created by carl on 8/7/14.
 */
object LongEventApp extends App{

  val executor = Executors.newCachedThreadPool()
  val factory = new LongEventFactory()

  // Specify the size of the ring buffer, must be power of 2.
  //1024*4 = 110 seconds
  //1024   = 112 seconds
  val bufferSize = 1024


  val disruptor = new Disruptor[LongEvent](factory, bufferSize, executor,ProducerType.SINGLE, new SleepingWaitStrategy())
  //set consumer/handler
  val logHandler = new LongEventHandler
  val gcmHandler = new GCMHandler
  val apnsHandler = new APNSHandler

  disruptor.handleEventsWith(logHandler)
  disruptor.after(logHandler).handleEventsWith(apnsHandler, gcmHandler)
  disruptor.start()

  val ringBuffer = disruptor.getRingBuffer()
  val producer = new LongEventProducer(ringBuffer)

  val beginTime = System.currentTimeMillis()
  for( a <- 1 to 2000){
    val message = Message(beginTime,a)
    producer.onData(message)
  }
}
