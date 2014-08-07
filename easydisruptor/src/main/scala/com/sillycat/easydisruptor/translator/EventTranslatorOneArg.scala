package com.sillycat.easydisruptor.translator

import com.sillycat.easydisruptor.model.LongEvent
import java.nio.ByteBuffer
import com.lmax.disruptor.EventTranslatorOneArg;

/**
 * Created by carl on 8/7/14.
 */
class CustomEventTranslatorOneArg extends EventTranslatorOneArg[LongEvent,ByteBuffer]{

  def translateTo(event: LongEvent, sequence: Long, bb: ByteBuffer) = {
    event.value = bb.getLong(0)
  }

}
