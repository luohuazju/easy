package com.sillycat.easydisruptor.translator

import com.sillycat.easydisruptor.model.{Message, LongEvent}
import java.nio.ByteBuffer
import com.lmax.disruptor.EventTranslatorOneArg;

/**
 * Created by carl on 8/7/14.
 */
class CustomEventTranslatorOneArg extends EventTranslatorOneArg[LongEvent,Message]{

  def translateTo(event: LongEvent, sequence: Long, message: Message) = {
    event.time = message.time
    event.number = message.number
  }

}
