package com.shadow.spark.core.sort

import org.apache.spark.{SparkConf, SparkContext}


/**
 * Class:
 * Author: wanghf
 * Date: 2017/7/11 0011  23:03
 * Descrption:  spark自定义分器的使用
  */
object secodrySort {

    def main(args: Array[String]): Unit = {
      if (args.length != 3) {
        println("Usage <number-of-partitions> <input-path> <output-path>")
        sys.exit(1)
      }

      val partitions = args(0).toInt
      val inputPath = args(1)
      val outputPath = args(2)

      val config = new SparkConf
      config.setAppName("SecondarySort").setMaster("local[*]")
      val sc = new SparkContext(config)

      val input = sc.textFile(inputPath)

      val valueToKey = input.map(x => {
        val line = x.split(",")
        ((line(0) + "-" + line(1), line(2).toInt), line(2).toInt)
      })

      // 上下文界定 传入一个隐式转化的值
      implicit def tupleOrderingDesc = new Ordering[Tuple2[String, Int]] {
        override def compare(x: Tuple2[String, Int], y: Tuple2[String, Int]): Int = {
          if (y._1.compare(x._1) == 0) y._2.compare(x._2)
          else y._1.compare(x._1)
        }
      }


      val sorted = valueToKey.repartitionAndSortWithinPartitions(new CustomPartitioner(partitions))

      val result = sorted.map {
        case (k, v) => (k._1, v)
      }

      result.saveAsTextFile(outputPath)
      // done
      sc.stop()
    }
}
