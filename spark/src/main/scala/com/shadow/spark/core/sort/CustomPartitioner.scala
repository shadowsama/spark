package com.shadow.spark.core.sort

import org.apache.spark.Partitioner



class CustomPartitioner(partitions: Int) extends Partitioner {
  
  require(partitions > 0, s"Number of partitions ($partitions) cannot be negative.")

  def numPartitions: Int = partitions

  def getPartition(key: Any): Int = key match {
    case (k: String, v: Int) => math.abs(k.hashCode % numPartitions)
    case null                => 0
    case _                   => math.abs(key.hashCode % numPartitions)
  }

  override def equals(other: Any): Boolean = other match {
    case h: CustomPartitioner => h.numPartitions == numPartitions
    case _                    => false
  }

  override def hashCode: Int = numPartitions
}
