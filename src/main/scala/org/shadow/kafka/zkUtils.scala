package org.shadow.kafka

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.{Lock, ReentrantLock}

import org.I0Itec.zkclient.{IZkChildListener, IZkDataListener, ZkClient}
import org.apache.zookeeper.CreateMode
import org.apache.zookeeper.ZooDefs.Ids
import org.apache.zookeeper.data.ACL
import org.shadow.core.Logging


class zkUtils {
}

object zkUtils extends Logging{
  val broker="/broker"
  val producter="/producter"
  val consumer="/comsumer"

  val lists =Array(broker,producter,consumer)

  val acl =Ids.OPEN_ACL_UNSAFE

  var zkClient:ZkClient = _

  def createZkClient(zkUrl: String, sessionTimeout: Int, connectionTimeout: Int): ZkClient = {
    val zkClient = new ZkClient(zkUrl, sessionTimeout, connectionTimeout)
    zkClient
  }

  def preStart(zkClient: ZkClient,path:String): Unit ={

    for (elem <- lists) {
     if(zkClient.exists(elem)){
     logInfo(s"$elem exists")
     }else{
       zkClient.create(elem,null,acl,CreateMode.PERSISTENT)
     }

    }
  }

  def createPersistent(client: ZkClient, path: String, data: Object, acls: java.util.List[ACL]) {
    client.createPersistent(path, data, acls)
  }

  def createPersistent(client: ZkClient, path: String, createParents: Boolean, acls: java.util.List[ACL]) {
    client.createPersistent(path, createParents, acls)
  }

  def createEphemeral(client: ZkClient, path: String, data: Object, acls: java.util.List[ACL]) {
    client.createEphemeral(path, data, acls)
  }

  def createPersistentSequential(client: ZkClient, path: String, data: Object, acls: java.util.List[ACL]): String = {
    client.createPersistentSequential(path, data, acls)
  }



  def producterChanged(): Unit ={

  }

  def main(args: Array[String]): Unit = {
    val client = zkUtils.createZkClient("master:2181",2000,2000)

    zkUtils.preStart(client,"")

  }


}



