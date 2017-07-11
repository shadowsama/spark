package org.shadow.kafka.broker

import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicBoolean

/**
  * Created by shadow on 2017/6/25 0025.
  */

case class EndPoint(val host: String,val port:Int){}

class Kserver {

  private val startupComplete = new AtomicBoolean(false)
  private val isShuttingDown = new AtomicBoolean(false)
  private val isStartingUp = new AtomicBoolean(false)
  private var shutdownLatch = new CountDownLatch(1)

}
