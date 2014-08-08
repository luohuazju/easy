package com.sillycat.easydisruptor.producer

import com.lmax.disruptor.RingBuffer
import java.nio.ByteBuffer
import com.sillycat.easydisruptor.translator.CustomEventTranslatorOneArg
import com.sillycat.easydisruptor.model.{Message, LongEvent}

/**
 * Created by carl on 8/7/14.
 */
class LongEventProducer(ringBuffer: RingBuffer[LongEvent]) {
  val translator = new CustomEventTranslatorOneArg()
  def onData(message:Message) = {
    println("Sending + " + message.number)
    ringBuffer.publishEvent(translator,message)
  }
}
