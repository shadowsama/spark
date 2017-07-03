package org.shadow.kafka.broker

import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicBoolean

import org.apache.kafka.common.utils.Utils

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

object Kserver{

  private val endPoint = EndPoint("192.168.109.1",9200)
  private val processor = new Processor
  private val acceptor = new Acceptor(endPoint,1024,1024,Array(processor))

  def startUp(): Unit ={

    new Thread(acceptor,"acceptor").start()
    acceptor.awaitStartup()

  }

  def main(args: Array[String]): Unit = {
    startUp()
  }

}
