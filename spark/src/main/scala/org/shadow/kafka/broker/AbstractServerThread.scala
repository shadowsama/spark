package org.shadow.kafka.broker

import java.net.{InetSocketAddress, SocketException}
import java.nio.channels.{SelectionKey, ServerSocketChannel, SocketChannel, Selector => NSelector}
import java.util.concurrent.{ConcurrentLinkedQueue, CountDownLatch}
import java.util.concurrent.atomic.AtomicBoolean

import kafka.cluster.BrokerEndPoint
import kafka.common.KafkaException
import kafka.network.{RequestChannel, TooManyConnectionsException}
import kafka.utils.CoreUtils
import org.apache.kafka.common.metrics.Metrics
import org.apache.kafka.common.network.{ChannelBuilders, LoginType, Mode}
import org.apache.kafka.common.utils.{Time, Utils}
import org.shadow.core.Logging

import scala.collection.{Map, mutable}
import scala.util.control.ControlThrowable


/** 
 * Class: 
 * Author: wanghf
 * Date: 2017/6/25 0025  20:55
 * Descrption:  Acceptor 和 process 抽象
 */
private[kafka] abstract  class AbstractServerThread extends Runnable with Logging  {

  private val startupLatch = new CountDownLatch(1)
  private val shutdownLatch = new CountDownLatch(1)
  private val alive = new AtomicBoolean(true)

  def wakeup()

  /**
    * Initiates a graceful shutdown by signaling to stop and waiting for the shutdown to complete
    */
  def shutdown(): Unit = {
    alive.set(false)
    wakeup()
    shutdownLatch.await()
  }

  /**
    * Wait for the thread to completely start up
    */
  def awaitStartup(): Unit = startupLatch.await

  /**
    * Record that the thread startup is complete
    */
  protected def startupComplete() = {
    startupLatch.countDown()
  }

  /**
    * Record that the thread shutdown is complete
    */
  protected def shutdownComplete() = shutdownLatch.countDown()

  /**
    * Is the server still running?
    */
  protected def isRunning = alive.get


  /**
    * Close `channel` and decrement the connection count.
    */
  def close(channel: SocketChannel) {
    if (channel != null) {
     logInfo("Closing connection from " + channel.socket.getRemoteSocketAddress())
      CoreUtils.swallow(channel.socket().close())
      CoreUtils.swallow(channel.close())
    }
  }

}

private[kafka] class Acceptor(val endPoint: EndPoint,val sendBufferSize:Int,
        val recvBufferSize:Int,  processors: Array[Processor]) extends AbstractServerThread {

  //1 打开路复用器
  private val nioSelector = NSelector.open()

  val serverChannel = openServerSocket(endPoint.host, endPoint.port)

//  this.synchronized {
//    processors.foreach { processor =>
//      //Utils.newThread("kafka-network-thread-%d-%s-%d".format(brokerId, endPoint.protocolType.toString, processor.id), processor, false).start()
//    }
//  }

  /**
    * Accept loop that checks for new connection attempts
    */
  def run() {

    // 把服务器通道注册到多路复用器上，并且监听阻塞事件
    serverChannel.register(nioSelector, SelectionKey.OP_ACCEPT)
    // acceptor 运行完成
    startupComplete()
    try {
      // 当前处理器
      var currentProcessor = 0
      // 原子变量 voltile
      while (isRunning) {
        try {
          //
          val ready = nioSelector.select(500)
          if (ready > 0) {
            //返回多路复用器已经选择的结果集
            val keys = nioSelector.selectedKeys()
            val iter = keys.iterator()
            while (iter.hasNext && isRunning) {
              try {
                //3 进行遍历
                val key = iter.next
                //4 直接移除
                iter.remove()
                if (key.isAcceptable)
                  accept(key, processors(currentProcessor))
                else
                  throw new IllegalStateException("Unrecognized key state for acceptor thread.")

                // round robin to the next processor thread
                currentProcessor = (currentProcessor + 1) % processors.length
              } catch {
                case e: Throwable => logError("Error while accepting connection", e)
              }
            }
          }
        }
        catch {
          // We catch all the throwables to prevent the acceptor thread from exiting on exceptions due
          // to a select operation on a specific channel or a bad request. We don't want the
          // the broker to stop responding to requests from other clients in these scenarios.
          case e: ControlThrowable => throw e
          case e: Throwable => logError("Error occurred", e)
        }
      }
    } finally {
      logDebug("Closing server socket and selector.")


      CoreUtils.swallow(serverChannel.close())
      CoreUtils.swallow(nioSelector.close())
      shutdownComplete()
    }
  }

  /*
   * Create a server socket to listen for connections on.
   */
  private def openServerSocket(host: String, port: Int): ServerSocketChannel = {
    val socketAddress =
      if (host == null || host.trim.isEmpty)
        new InetSocketAddress(port)
      else
        new InetSocketAddress(host, port)
    //2 打开服务器通道
    val serverChannel = ServerSocketChannel.open()
    //3 设置服务器通道为非阻塞模式
    serverChannel.configureBlocking(false)
    serverChannel.socket().setReceiveBufferSize(recvBufferSize)
    try {
      serverChannel.socket.bind(socketAddress)
      logInfo("Awaiting socket connections on %s:%d.".format(socketAddress.getHostString, serverChannel.socket.getLocalPort))
    } catch {
      case e: SocketException =>
        throw new KafkaException("Socket server failed to bind to %s:%d: %s.".format(socketAddress.getHostString, port, e.getMessage), e)
    }
    serverChannel
  }


 /**
  * Class: Acceptor
  * Author: wanghf
  * Date: 2017/6/25 0025  21:57
  * Descrption: 一个新的链接
  */
  def accept(key: SelectionKey, processor: Processor) {
    //1 获取服务通道
    val serverSocketChannel = key.channel().asInstanceOf[ServerSocketChannel]
    //2 执行阻塞方法
    val socketChannel = serverSocketChannel.accept()
    try {
      socketChannel.configureBlocking(false)
      socketChannel.socket().setTcpNoDelay(true)
      socketChannel.socket().setKeepAlive(true)
      socketChannel.socket().setSendBufferSize(sendBufferSize)

//      logInfo("Accepted connection from %s on %s and assigned it to processor %d, sendBufferSize [actual|requested]: [%d|%d] recvBufferSize [actual|requested]: [%d|%d]"
//        .format(socketChannel.socket.getRemoteSocketAddress, socketChannel.socket.getLocalSocketAddress, processor.id,
//          socketChannel.socket.getSendBufferSize, sendBufferSize,
//          socketChannel.socket.getReceiveBufferSize, recvBufferSize))

      processor.accept(socketChannel)
    } catch {
      case e: TooManyConnectionsException =>
        logInfo("Rejected connection from %s, address already has the configured maximum of %d connections.".format(e.ip, e.count))
        close(socketChannel)
    }
  }

  override def wakeup(): Unit = {}
}


private[kafka] class Processor extends AbstractServerThread{
  override def wakeup(): Unit ={}

  override def run(): Unit = {}

  def accept(socketChannel:SocketChannel)={

  }
}

//private[kafka] class Processor(val id: Int,
//                                 time: Time,
//                                 maxRequestSize: Int,
//                                 requestChannel: RequestChannel,
//                                 channelConfigs: java.util.Map[String, _],
//                                 metrics: Metrics) extends AbstractServerThread() {
//
//  private object ConnectionId {
//    def fromString(s: String): Option[ConnectionId] = s.split("-") match {
//      case Array(local, remote) => BrokerEndPoint.parseHostPort(local).flatMap { case (localHost, localPort) =>
//        BrokerEndPoint.parseHostPort(remote).map { case (remoteHost, remotePort) =>
//          ConnectionId(localHost, localPort, remoteHost, remotePort)
//        }
//      }
//      case _ => None
//    }
//  }
//
//  private case class ConnectionId(localHost: String, localPort: Int, remoteHost: String, remotePort: Int) {
//    override def toString: String = s"$localHost:$localPort-$remoteHost:$remotePort"
//  }
//
//  private val newConnections = new ConcurrentLinkedQueue[SocketChannel]()
//  private val inflightResponses = mutable.Map[String, RequestChannel.Response]()
//
//  private val selector = new KSelector(
//    maxRequestSize,
//    metrics,
//    time,
//    "socket-server",
//    false,
//    ChannelBuilders.create(protocol, Mode.SERVER, LoginType.SERVER, channelConfigs, null, true))
//
//  override def run() {
//    startupComplete()
//    while (isRunning) {
//      try {
//        // setup any new connections that have been queued up
//        configureNewConnections()
//        // register any new responses for writing
//        processNewResponses()
//        poll()
//        processCompletedReceives()
//        processCompletedSends()
//        processDisconnected()
//      } catch {
//        // We catch all the throwables here to prevent the processor thread from exiting. We do this because
//        // letting a processor exit might cause a bigger impact on the broker. Usually the exceptions thrown would
//        // be either associated with a specific socket channel or a bad request. We just ignore the bad socket channel
//        // or request. This behavior might need to be reviewed if we see an exception that need the entire broker to stop.
//        case e: ControlThrowable => throw e
//        case e: Throwable =>
//         logError("Processor got uncaught exception.", e)
//      }
//    }
//
//    logDebug("Closing selector - processor " + id)
//    //(closeAll())
//    shutdownComplete()
//  }
//}