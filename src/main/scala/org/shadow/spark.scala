package org.shadow

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession


/**
 * Class: 
 * User: shadow
 * Date: 2017/6/15 0015  1:00
 * Descrption: workCounnt with spark
 */
object spark{

  def main(args: Array[String]): Unit = {

      val ss=  SparkSession.builder().appName("wc").master("local[*]").getOrCreate();
      val sc = ss.sparkContext

      val unit: RDD[String] = sc.textFile("D:\\_20170526\\readme.md").flatMap(_.split(" "))

      // map and flat
      unit.foreach(println(_))
    
      val value: RDD[(String, Int)] = unit.map((_,1))

      val reduceByKey: RDD[(String, Int)] = value.reduceByKey(_+_)

      val array = reduceByKey.collect()

      array.foreach(println)

  }
}
  





 

