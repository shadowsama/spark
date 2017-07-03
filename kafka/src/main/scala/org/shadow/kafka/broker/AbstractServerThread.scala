package org.shadow.kafka.broker

import java.io.{EOFException, IOException}
import java.lang.String.format
import java.net.{InetSocketAddress, Socket, SocketException}
import java.nio.ByteBuffer
import java.nio.channels.{ClosedChannelException, SelectionKey, ServerSocketChannel, SocketChannel, Selector => NSelector}
import java.util
import java.util.Iterator
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
private[kafka] abstract class AbstractServerThread extends Runnable with Logging {

  private val startupLatch = new CountDownLatch(1)
  private val shutdownLatch = new CountDownLatch(1)
  private val alive = new AtomicBoolean(true)

  def wakeup()

  def shutdown(): Unit = {
    alive.set(false)
    wakeup()
    shutdownLatch.await()
  }

  def awaitStartup(): Unit = startupLatch.await

  protected def startupComplete() = {
    startupLatch.countDown()
  }

  protected def shutdownComplete() = shutdownLatch.countDown()

  protected def isRunning = alive.get

  def close(channel: SocketChannel) {
    if (channel != null) {
      logInfo("Closing connection from " + channel.socket.getRemoteSocketAddress())
      CoreUtils.swallow(channel.socket().close())
      CoreUtils.swallow(channel.close())
    }
  }

}

private[kafka] class Acceptor(val endPoint: EndPoint, val sendBufferSize: Int,
                              val recvBufferSize: Int, processors: Array[Processor]) extends AbstractServerThread {

  //1 打开路复用器
  private val nioTcpSelector = NSelector.open()

  val serverChannel = openServerSocket(endPoint.host, endPoint.port)

  this.synchronized {
    processors.foreach { processor =>
      logInfo(s"$processor started")
      new Thread(processor, "processor").start()
    }
  }

  /**
    * Accept loop that checks for new connection attempts
    */
  def run() {

    // 把服务器通道注册到多路复用器上，并且监听阻塞事件
    serverChannel.register(nioTcpSelector, SelectionKey.OP_ACCEPT)
    // acceptor 运行完成
    startupComplete()
    logInfo("acceptor running!!!")
    try {
      // 当前处理器
      var currentProcessor = 0
      // 原子变量 voltile
      while (isRunning) {
        try {
          // 500ms 调用一次， 没有加时间则一直阻塞中
          val ready = nioTcpSelector.select(500)
          if (ready > 0) {
            //返回多路复用器已经选择的结果集
            val keys = nioTcpSelector.selectedKeys()
            val iter = keys.iterator()
            while (iter.hasNext && isRunning) {
              try {
                //3 进行遍历
                val key = iter.next
                //4 直接移除
                iter.remove()
                if (key.isAcceptable) {
                  logInfo(s"acceped connection $key")

                  accept(key, processors(currentProcessor))
                }

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
      CoreUtils.swallow(nioTcpSelector.close())
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
      // 4 监听端口的tcp连接
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
    //2 执行阻塞方法, 获取socketChannel
    val socketChannel = serverSocketChannel.accept()
    try {
      socketChannel.configureBlocking(false)
      socketChannel.socket().setTcpNoDelay(true)
      socketChannel.socket().setKeepAlive(true)
      socketChannel.socket().setSendBufferSize(sendBufferSize)

      processor.accept(socketChannel)
    } catch {
      case e: TooManyConnectionsException =>
        logInfo("Rejected connection from %s, address already has the configured maximum of %d connections.".format(e.ip, e.count))
        close(socketChannel)
    }
  }

  override def wakeup(): Unit = {
    nioTcpSelector.wakeup()
  }
}

private[kafka] class Processor() extends AbstractServerThread() {


  private val readBuf = ByteBuffer.allocate(1024)
  private val newConnections = new ConcurrentLinkedQueue[SocketChannel]()
  private val inflightResponses = mutable.Map[String, RequestChannel.Response]()

  private var selector:NSelector = null

  /**
    * Queue up a new connection for reading
    */
  def accept(socketChannel: SocketChannel) {
    newConnections.add(socketChannel)
    logInfo(s"$this add socketChannel to queen ${newConnections.size()} ")
   // wakeup()
  }

  private def channelFor(key: SelectionKey) = key.channel.asInstanceOf[SocketChannel]

  @throws[IOException]
  private def read(key: SelectionKey) = {

    logInfo(s"read data")
    val socketChannel = channelFor(key)
    var request = null

    //1 清空缓冲区旧的数据
    this.readBuf.clear
    //2 获取之前注册的socket通道对象
    val sc = key.channel.asInstanceOf[SocketChannel]
    //3 读取数据
    val count = sc.read(this.readBuf)
    //4 如果没有数据
    if (count == -1) {
      key.channel.close()
      key.cancel()
    }
    //5 有数据则进行读取 读取之前需要进行复位方法(把position 和limit进行复位)
    this.readBuf.flip
    //6 根据缓冲区的数据长度创建相应大小的byte数组，接收缓冲区的数据
    val bytes = new Array[Byte](this.readBuf.remaining)
    //7 接收缓冲区数据
    this.readBuf.get(bytes)
    //8 打印结果
    val body = new String(bytes).trim
    System.out.println("Server : " + body)
  }

  @throws[ClosedChannelException]
  private def configureNewConnections() = {


   if(!newConnections.isEmpty) {
      var channel = newConnections.poll
      logInfo("Listening to new connection from " + channel.socket.getRemoteSocketAddress)
      try {
        val selectionKey = channel.register(selector, SelectionKey.OP_READ)
        logInfo(s"$selectionKey")
      } catch {
        case e => e.printStackTrace()
      }
    }
  }

  /**
    * @return the selector
    */
  def getSelector = {
    if (selector == null) try
      selector = NSelector.open()
    catch {
      case e: IOException =>
        throw new RuntimeException(e)
    }
    selector
  }

  override def run() {
    startupComplete()

    // null pontion
    getSelector
    while (true) {

      configureNewConnections()
      selector
      var ready = getSelector.select(500)


      if (ready > 0) {

        val iter = getSelector.selectedKeys.iterator
        logInfo(s"a valid connection")
        while ( {
          iter.hasNext && isRunning
        }) {

          var key = iter.next
          iter.remove()
          if (key.isReadable) read(key)
          //else if (key.isWritable)// write(key)
          //else if (!key.isValid) // close(key)
          //  else throw new IllegalStateException("Unrecognized key state for processor thread.")


        }
      }


    }


    //(closeAll())
    //shutdownComplete()
  }

  override def wakeup(): Unit = {
    getSelector.wakeup()
  }
}