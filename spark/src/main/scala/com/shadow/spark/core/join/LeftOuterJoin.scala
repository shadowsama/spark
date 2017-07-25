package com.shadow.spark.core.join

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext


object LeftOuterJoin {

  def main(args: Array[String]): Unit = {
    if (args.size < 3) {
      println("Usage: LeftOuterJoin <users-data-path> <transactions-data-path> <output-path>")
      sys.exit(1)
    }

    val sparkConf = new SparkConf().setAppName("LeftOuterJoin")
    val sc = new SparkContext(sparkConf)

    val usersInputFile = args(0)
    val transactionsInputFile = args(1)
    val output = args(2)

    val usersRaw = sc.textFile(usersInputFile)
    val transactionsRaw = sc.textFile(transactionsInputFile)

    val users = usersRaw.map(line => {
      val tokens = line.split("\t")
      (tokens(0), ("L", tokens(1))) // Tagging Locations with L
    })

    val transactions = transactionsRaw.map(line => {
      val tokens = line.split("\t")
      (tokens(2), ("P", tokens(1))) // Tagging Products with P
    })

    // This operation is expensive and is listed to compare with Hadoop 
    // MapReduce approach, please compare it with more optimized approach 
    // shown in SparkLeftOuterJoin.scala or DataFramLeftOuterJoin.scala
    val all = users union transactions

    val grouped = all.groupByKey()

    val productLocations = grouped.flatMap {
      case (userId, iterable) =>
        // span returns two Iterable, one containing Location and other containing Products 
        val (location, products) = iterable span (_._1 == "L")
        val loc = location.headOption.getOrElse(("L", "UNKNOWN"))
        products.filter(_._1 == "P").map(p => (p._2, loc._2)).toSet
    }
    //
    val productByLocations = productLocations.groupByKey()

    val result = productByLocations.map(t => (t._1, t._2.size)) // Return (product, location count) tuple

    result.saveAsTextFile(output) // Saves output to the file.

    // done
    sc.stop()
  }
}
