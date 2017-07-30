package com.shadow.spark.core.join

import java.util.concurrent.TimeUnit

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD


/** 
 * Class: 
 * Author: wanghf
 * Date: 2017/7/25 0025  22:09
 * Descrption: use Rdd to release join function
 */
object SparkLeftOuterJoin {
  
  def main(args: Array[String]): Unit = {


    val args = Array("spark\\src\\main\\scala\\com\\shadow\\spark\\core\\join\\users.tsv",
      "spark\\src\\main\\scala\\com\\shadow\\spark\\core\\join\\transactions.tsv",
      "spark-warehouse\\join")

    
    val sparkConf = new SparkConf().setAppName("SparkLeftOuterJoin").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)

    val usersInputFile =args(0)
    val transactionsInputFile = args(1)
    val output = args(2)

    val usersRaw = sc.textFile(usersInputFile)
    val transactionsRaw = sc.textFile(transactionsInputFile)

    val users = usersRaw.map(line => {
      val tokens = line.split("\t")
      //user location
      (tokens(0), tokens(1))
    })

    val transactions = transactionsRaw.map(line => {
      val tokens = line.split("\t")
      //user product
      (tokens(2), tokens(1))
    })

    val joined: RDD[(String, (String, Option[String]))] =  transactions leftOuterJoin users
    // getOrElse is a method availbale on Option which either returns value 
    // (if present) or returns passed value (unknown in this case).
    val productLocations = joined.map(f => (f._1, (f._2)))

    productLocations.foreach(println(_))
//    (u5,(p4,Some(GA)))
//    (u4,(p4,Some(CA)))
//    (u1,(p3,Some(UT)))
//    (u1,(p1,Some(UT)))
//    (u1,(p1,Some(UT)))
//    (u1,(p4,Some(UT)))
//    (u2,(p1,Some(GA)))
//    (u2,(p2,Some(GA)))
  
    val productByLocations = productLocations.groupByKey()

    val productWithUniqueLocations = productByLocations.mapValues(_.toSet) // Converting toSet removes duplicates.

     val result = productWithUniqueLocations.map(t => (t._1, t._2.size)) // Return (product, location count) tuple.
//
 //   result.saveAsTextFile(output) // Saves output to the file.

    // done
    TimeUnit.SECONDS.sleep(Int.MaxValue)
    sc.stop()
  }
}
