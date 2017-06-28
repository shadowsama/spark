package org.shadow.kafka

/**
  * Created by shadow on 2017/6/17 0017.
  */
import java.util.concurrent.TimeUnit
import kafka.common.TopicAndPartition
import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.framework.recipes.locks.InterProcessMutex
import org.apache.curator.retry.ExponentialBackoffRetry
import org.slf4j.LoggerFactory
import scala.collection.JavaConversions._


//  @See http://blog.csdn.net/silentwolfyh/article/details/52985171
object zkOperate{

  val LOG = LoggerFactory.getLogger(zkOperate.getClass)
  val client = {
    val client = CuratorFrameworkFactory
      .builder
      .connectString("slave01:2181,slave02:2181,slave03:2181")
      .retryPolicy(new ExponentialBackoffRetry(1000, 3))
      .namespace("yuhui_test")
      .build()
    client.start()
    client
  }

  def lock(path: String)(body: => Unit) {
    val lock = new InterProcessMutex(client, path)
    lock.acquire()
    try {
      body
    } finally {
      lock.release()
    }

  }

  def tryDo(path: String)(body: => Unit): Boolean = {
    val lock = new InterProcessMutex(client, path)
    if (!lock.acquire(10, TimeUnit.SECONDS)) {
      LOG.info(s"不能获得锁 {$path}，已经有任务在运行，本次任务退出")
      return false
    }
    try {
      LOG.info("获准运行")
      body
      true
    } finally {
      lock.release()
      LOG.info(s"释放锁 {$path}")
    }

  }

  //zookeeper创建路径
  def ensurePathExists(path: String): Unit = {
    if (client.checkExists().forPath(path) == null) {
      client.create().creatingParentsIfNeeded().forPath(path)
    }
  }

  //zookeeper提取offset的方法
  def loadOffsets(topicSet: Set[String], defaultOffset: Map[TopicAndPartition, Long]): Map[TopicAndPartition, Long] = {
    val kafkaOffsetPath = s"/kafkaOffsets"
    ensurePathExists(kafkaOffsetPath)
    val offsets = for {
    //t就是路径webstatistic/kafkaOffsets下面的子目录遍历
      t <- client.getChildren.forPath(kafkaOffsetPath)
      if topicSet.contains(t)
      //p就是新路径   /webstatistic/kafkaOffsets/donews_website
      p <- client.getChildren.forPath(s"$kafkaOffsetPath/$t")
    } yield {
      //遍历路径下面的partition中的offset
      val data = client.getData.forPath(s"$kafkaOffsetPath/$t/$p")
      //将data变成Long类型
      val offset = java.lang.Long.valueOf(new String(data)).toLong
      (TopicAndPartition(t, Integer.parseInt(p)), offset)
    }
    defaultOffset ++ offsets.toMap
  }

  //zookeeper存储offset的方法
  def storeOffsets(offsets: Map[TopicAndPartition, Long]): Unit = {
    val kafkaOffsetPath = s"/kafkaOffsets"
    if (client.checkExists().forPath(kafkaOffsetPath) == null) {
      client.create().creatingParentsIfNeeded().forPath(kafkaOffsetPath)
    }
    for ((tp, offset) <- offsets) {
      val data = String.valueOf(offset).getBytes
      val path = s"$kafkaOffsetPath/${tp.topic}/${tp.partition}"
      ensurePathExists(path)
      client.setData().forPath(path, data)
    }
  }

  def main(args: Array[String]) {

    //获取到namespace
    println(client.getNamespace)

    //创建路径
    val kafkaOffsetPath = "/kafkaOffsets"
    if (client.checkExists().forPath(kafkaOffsetPath) == null) {
      client.create().creatingParentsIfNeeded().forPath(kafkaOffsetPath)
    }

    //删除路径
    client.delete().forPath("/kafkaOffsets/web/1")

    //存储值
    val offsets : Map[TopicAndPartition, Long] = Map(TopicAndPartition("web",1) ->4444, TopicAndPartition("web",2)->2222 )
    storeOffsets(offsets)

    //获取值
    val topicSet = Set("web")
    val offsetstoMap:Map[TopicAndPartition, Long]= loadOffsets(topicSet,Map(TopicAndPartition("web",1) ->0))
    offsetstoMap.keySet.foreach(line=>{
      val  topicName = line.topic
      val  topicPartition = line.partition
      val data = client.getData.forPath(s"$kafkaOffsetPath/$topicName/$topicPartition")
      val offset = java.lang.Long.valueOf(new String(data)).toLong
      println("路径"+s"$kafkaOffsetPath/$topicName/$topicPartition"+"的值为:"+ offset)
    })

  }

}