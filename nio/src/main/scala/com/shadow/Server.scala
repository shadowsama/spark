package com.shadow

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.{SelectionKey, Selector, ServerSocketChannel, SocketChannel}
import java.util
import java.util.Set

import scala.util.control.Breaks


/**
 * Class:
 * Author: wanghf
 * Date: 2017/6/27 0027  19:53
 * Descrption:
 */
object Server {

  private var echoBuffer = ByteBuffer.allocate(1024)
  def main(args: Array[String]): Unit = {

    try {
      // 多路复用选择器
     var selector = Selector.open
      //  服务端接收连接
     var ssc = ServerSocketChannel.open

      // 设置为非阻塞
      ssc.configureBlocking(false)

      // ssc构造一个socket
     // var socket = ssc.socket

     var socketAddress = new InetSocketAddress(Integer.parseInt(args(0)))

      println(s"listning on port ${args(0)}")
      ssc.bind(socketAddress)
      //将新打开的 ServerSocketChannels 注册到 Selector上。为此我们使用 ServerSocketChannel.register() 方法
      ssc.register(selector, SelectionKey.OP_ACCEPT)

      while (true){
        // 阻塞 监听连建来的连接
       var int: Int = selector.select()


       println(s"$int socket connected now ")

       var keys: util.Set[SelectionKey] = selector.selectedKeys()

       var iter = keys.iterator()

        while (iter.hasNext) {

         var selectionKey = iter.next()

          // 监听新连接
          //程序执行到这里，我们仅注册了 ServerSocketChannel，并且仅注册它们“接收”事件。为确认这一点，我们对 SelectionKey 调用 readyOps() 方法，并检查发生了什么类型的事件：
          if ((selectionKey.readyOps() & SelectionKey.OP_ACCEPT)
            == SelectionKey.OP_ACCEPT) {

            //因为我们知道这个服务器套接字上有一个传入连接在等待，所以可以安全地接受它；也就是说，不用担心 accept() 操作会阻塞：
           var ssc = selectionKey.channel.asInstanceOf[ServerSocketChannel]
           var sc = ssc.accept
            // 下一步是将新连接的 SocketChannel 配置为非阻塞的。而且由于接受这个连接的目的是为了读取来自套接字的数据，所以我们还必须将 SocketChannel 注册到 Selector上，如下所示：
            sc.configureBlocking(false)
           var newKey = sc.register(selector, SelectionKey.OP_READ)

            // 在处理 SelectionKey 之后，我们几乎可以返回主循环了。但是我们必须首先将处理过的 SelectionKey 从选定的键集合中删除。如果我们没有删除处理过的键，那么它仍然会在主集合中以一个激活的键出现，这会导致我们尝试再次处理它。我们调用迭代器的 remove() 方法来删除处理过的 SelectionKey
            iter.remove
            println("Got connection from " + sc)
          //  selector.wakeup()
          } else if ((selectionKey.readyOps() & SelectionKey.OP_READ)
            == SelectionKey.OP_READ) {
            // Read the data
           var sc = selectionKey.channel.asInstanceOf[SocketChannel]

                      // Echo data
                     var bytesEchoed = 0
                      import scala.util.control.Breaks._

                      while (true) {
                        println(s"reading data ")
                        breakable {
                          echoBuffer.clear
                          var r = sc.read(echoBuffer)
                          if (r <= 0) {
                            break()
                          }
                          echoBuffer.flip
                          sc.write(echoBuffer)
                          println(s"write data  ")
                          bytesEchoed += r
                        }
                      }

            iter.remove()
            //selector.wakeup()
          }
        }
      }
    }catch {
      case e => e.printStackTrace()
    }
  }

}

class Server{

}