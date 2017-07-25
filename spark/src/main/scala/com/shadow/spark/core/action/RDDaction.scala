package com.shadow.spark.core.action

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Created by shadow on 2017/7/19 0019.
  */
object RDDaction {


  /**
    * * Internally, each RDD is characterized by five main properties:
    *
    *  - A list of partitions
    *  - A function for computing each split
    *  - A list of dependencies on other RDDs
    *  - Optionally, a Partitioner for key-value RDDs (e.g. to say that the RDD is hash-partitioned)
    *  - Optionally, a list of preferred locations to compute each split on (e.g. block locations for
    * an HDFS file)
    *
    * @param args
    */

  def main(args: Array[String]): Unit = {


    val spark = SparkSession
      .builder
      .master("local[*]")
      .appName("Action")
      .getOrCreate()


    val sc = spark.sparkContext
    val rdd1: RDD[Int] = sc.parallelize(List(1, 2, 3, 4, 5,6,7,8,9,0), 2)

    // ParallelCollectionRDD[T]
    {


      val rddInt = (index: Int, iter: Iterator[Int]) => {

        iter.toList.map(x => "[partID:" + index + ", val: " + x + "]").iterator
      }

      val rdd2: RDD[String] = rdd1.mapPartitionsWithIndex(rddInt)
      rdd2.foreach(println)


      val value = rdd1.cartesian(rdd2)

      value.foreach(println(_))


    }
    def func1(index: Int, iter: Iterator[(Int)]): Iterator[String] = {
      iter.toList.map(x => "[partID:" + index + ", val: " + x + "]").iterator
    }

    {


      // 对迭代器中的内容使用折叠函数局部聚合，最后对局部聚合的结果进行job聚合

      //      val aggregatePartition = (it: Iterator[T]) => it.aggregate(zeroValue)(cleanSeqOp, cleanCombOp)
      //      val mergeResult = (index: Int, taskResult: U) => jobResult = combOp(jobResult, taskResult)
      //      sc.runJob(this, aggregatePartition, mergeResult)
      //      jobResult

      val i: Int = rdd1.aggregate(0)(_ + _, _ + _)
      println(i)
    }


    // combineByKey
    {
      val rdd2: RDD[(String, Int)] = sc.textFile(args(0)).flatMap(_.split(",")).map((_, 1))

      val rdd3 = rdd2.reduceByKey(_ + _)

      val createCombiner = (x: Int) => x
      val mergeValue = (x: Int, y: Int) => x + y
      val mergeCombiners = (x: Int, y: Int) => (x + y)

      // 必须指定ClassTag ，否则报错
      val unit: RDD[(String, Int)] = rdd2.combineByKey(createCombiner, mergeValue, mergeCombiners)
    }

    //repartion

    {

      rdd1.partitions.length

      val coalesc: RDD[Int] = rdd1.coalesce(3,true)

      coalesc.mapPartitionsWithIndex(func1).collect().foreach(println)

    }


  }


}
