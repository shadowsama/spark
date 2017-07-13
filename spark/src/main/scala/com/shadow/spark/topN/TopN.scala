package com.shadow.spark.topN

import org.apache.spark.TaskContext
import org.apache.spark.rdd.{MapPartitionsRDD, RDD}
import org.apache.spark.sql.SparkSession

import scala.collection.SortedMap
import scala.reflect.ClassTag


class TopN {

}


object TopN {




  def main(args: Array[String]): Unit = {

    val spark = SparkSession.
      builder().
      appName("TopN")
        .master("local[*]")
      .getOrCreate()


    val sc = spark.sparkContext

   val rdd1 =  sc.textFile("file:\\D:\\_20170526\\spark\\src\\main\\scala\\com\\shadow\\spark\\topN\\input").map(l => {
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


      rdd1.mapPartitionsWithIndex(func).collect().foreach(println)


      spark.stop()



  }


}