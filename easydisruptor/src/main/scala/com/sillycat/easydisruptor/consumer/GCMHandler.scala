package com.sillycat.easydisruptor.consumer

import com.lmax.disruptor.EventHandler
import com.sillycat.easydisruptor.model.LongEvent

/**
 * Created by carl on 8/7/14.
 */
class GCMHandler extends EventHandler[LongEvent]{

  def onEvent(event:LongEvent,sequence:Long,endOfBatch:Boolean): Unit ={
    Thread.sleep(40)
    println(Thread.currentThread().getName + " GCM  Event(" + event.number +"): " + (System.currentTimeMillis() - event.time) / 1000 + " s")
  }

}
