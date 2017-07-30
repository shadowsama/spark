package com.shadow.spark.core.topN

import java.util.concurrent.Executors

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.shadow.core.Logging

/**
  * Created by shadow on 2017/7/19 0019.
  */
object TopN extends Logging{


  private  val threadPool = Executors.newCachedThreadPool
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.
      builder().
      appName("TopN7")
      .master("local[1]")
      .getOrCreate()


    val sc = spark.sparkContext

    val rdd1 : RDD[((String,Array[String]))] =  sc.textFile(Option(args(0)).getOrElse("hdfs://master:9000/topN/topN")).map(l => {
       val token = l.split(",")
       (token(2), token)})

    /**
      * Return a new RDD by applying a function to each partition of this RDD, while tracking the index
      * of the original partition.
      *
      * `preservesPartitioning` indicates whether the input function preserves the partitioner, which
      * should be `false` unless this is a pair RDD and the input function doesn't modify the keys.
      */
//    def mapPartitionsWithIndex[U: ClassTag](
//                                             f: (Int, Iterator[T]) => Iterator[U],
//                                             preservesPartitioning: Boolean = false): RDD[U] = withScope {
//      val cleanedF = sc.clean(f)
//      new MapPartitionsRDD(
//        this,
//        (context: TaskContext, index: Int, iter: Iterator[T]) => cleanedF(index, iter),
//        preservesPartitioning)
//    }

    val func = (index: Int, iter: Iterator[((String,Array[String]))]) => {

       iter.toList.map(x => "[partID:" +  index + ", val: " + x + "]").iterator
    }

    rdd1.mapPartitionsWithIndex(func).collect().foreach(logInfo(_))





    //Thread.sleep(Integer.MAX_VALUE)
    spark.stop()



  }


}
