package org.shadow

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Created by shadow on 2017/6/17 0017.
  */
object saprkcsv {


  case class person(var order_sn:String,var pay_company_commission_fee: BigDecimal,var merchant_commission_fee: BigDecimal)

  def main(args: Array[String]): Unit = {

    val spark=  SparkSession.builder().appName("jdbc").master("local[*]").getOrCreate();

    spark.sparkContext.addJar("D:\\m2\\20170511\\mysql\\mysql-connector-java\\5.1.42\\mysql-connector-java-5.1.42.jar")
    import spark.implicits._
    val jdbcDF = spark.read.format("jdbc").options(
      Map("url" -> "jdbc:mysql://localhost:3306/fs_liquidation_platform?user=fXX&password=XXX",
        "dbtable" -> s"""(SELECT order_sn ,pay_company_commission_fee,merchant_commission_fee FROM `fbs_day_order`
        ) t""".stripMargin,
        "driver" ->"com.mysql.jdbc.Driver",
        "fetchSize" -> "1"
      )).load().as[person]

    jdbcDF.createTempView("fbs_day_order")

    import spark.sql

    jdbcDF.write.format("csv").csv("1.csv")





    // spark.close()



  }
}
