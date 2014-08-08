package com.sillycat.easydisruptor.consumer

import com.lmax.disruptor.WorkHandler
import com.sillycat.easydisruptor.model.LongEvent

/**
 * Created by carl on 8/7/14.
 */
class APNSWorkHandler extends WorkHandler[LongEvent]{

  def onEvent(event: LongEvent): Unit = {
    Thread.sleep(40)
    println(Thread.currentThread().getName + " APNS Event(" + event.number +"): " + (System.currentTimeMillis() - event.time)/1000 + " s")
  }

}
