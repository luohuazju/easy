package com.sillycat.easydisruptor.producer

import com.lmax.disruptor.RingBuffer
import java.nio.ByteBuffer
import com.sillycat.easydisruptor.translator.CustomEventTranslatorOneArg
import com.sillycat.easydisruptor.model.LongEvent

/**
 * Created by carl on 8/7/14.
 */
class LongEventProducer(ringBuffer: RingBuffer[LongEvent]) {
  val translator = new CustomEventTranslatorOneArg()
  def onData(bb:ByteBuffer) = {
    ringBuffer.publishEvent(translator,bb)
  }
}
