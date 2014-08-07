package com.sillycat.easydisruptor

import java.util.concurrent.Executors
import com.lmax.disruptor.SleepingWaitStrategy
import com.sillycat.easydisruptor.factory.LongEventFactory
import com.lmax.disruptor.dsl.{ProducerType, Disruptor}
import com.sillycat.easydisruptor.consumer.LongEventHandler
import com.sillycat.easydisruptor.producer.LongEventProducer
import java.nio.ByteBuffer
import com.sillycat.easydisruptor.model.LongEvent

/**
 * Created by carl on 8/7/14.
 */
object LongEventApp extends App{
  val executor = Executors.newCachedThreadPool()
  val factory = new LongEventFactory()

  // Specify the size of the ring buffer, must be power of 2.
  val bufferSize = 1024

  val disruptor = new Disruptor[LongEvent](factory, bufferSize, executor,ProducerType.SINGLE, new SleepingWaitStrategy())
  //set consumer/handler
  disruptor.handleEventsWith(new LongEventHandler())
  disruptor.start()

  val ringBuffer = disruptor.getRingBuffer()
  val producer = new LongEventProducer(ringBuffer)

  val bb = ByteBuffer.allocate(8);
  for( a <- 1 to 10){
    bb.putLong(0, a)
    producer.onData(bb)
    Thread.sleep(1000)
  }
}
