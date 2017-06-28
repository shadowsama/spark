package org.shadow.zk

import java.util.concurrent.CountDownLatch

import org.apache.zookeeper.Watcher.Event.KeeperState
import org.apache.zookeeper.ZooDefs.Ids
import org.apache.zookeeper.{CreateMode, WatchedEvent, Watcher, ZooKeeper}
import org.shadow.core.Logging
import org.slf4j.LoggerFactory

/**
  * Created by shadow on 2017/6/15 0015.
  *
  * zk create a groupNode
  *
  */
class CreateGroup extends Watcher with Logging{


  val SESSION_TIMEOUT = 5000;
  var zk: ZooKeeper = _
  var countDownLatch: CountDownLatch = new CountDownLatch(1)

  override def process(watchedEvent: WatchedEvent) = {

   try {
     if (watchedEvent.getState == KeeperState.SyncConnected) {
       countDownLatch.countDown()
     }
   } catch {
     case e =>
   }
    

  }

  def connect(hosts: String) = {
    try {
      zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this)
      countDownLatch.await()
    } catch {
      case e => e.printStackTrace()
    }
  }



  def createNode(groupName:String)={

    zk.create("/"+groupName,null/**date*/,Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT)

    logInfo(s"create ${groupName}")

  }

  def close(): Unit ={
    zk.close()
  }


}

object CreateGroup{

  def main(args: Array[String]): Unit = {

    val group = new CreateGroup()
    group.connect("master")
    group.createNode("zk2")
    group.close()

  }
}
