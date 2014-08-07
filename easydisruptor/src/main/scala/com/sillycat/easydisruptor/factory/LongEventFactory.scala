package com.sillycat.easydisruptor.factory

import com.lmax.disruptor.EventFactory
import com.sillycat.easydisruptor.model.LongEvent

/**
 * Created by carl on 8/7/14.
 */
class LongEventFactory extends EventFactory[LongEvent] {
  def  newInstance():LongEvent = {
    return new LongEvent()
  }
}
