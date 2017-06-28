package com.shadow.jvm;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class jdbcRdd implements Serializable {


    private static final long serialVersionUID = -8513279306224995844L;

    private static final Logger LOGGER = LoggerFactory.getLogger(jdbcRdd.class);

    private static final String MYSQL_USERNAME = "XXX";
    private static final String MYSQL_PWD = "XXXX";
    private static final String MYSQL_CONNECTION_URL = "jdbc:mysql://localhost:3306/fs_liquidation_platform?user=fXX&password=XXX";

    private static final JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("SparkJdbcFromDb").setMaster("local[*]"));

    private static final SQLContext sqlContext = new SQLContext(sc);

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put("user", MYSQL_USERNAME);
        properties.put("password", MYSQL_PWD);
        // Load MySQL query result as DataFrame
        Dataset<Row> jdbcDF = sqlContext.read().jdbc(MYSQL_CONNECTION_URL, "fbs_day_order", properties);

        List<Row> employeeFullNameRows = jdbcDF.collectAsList();

        jdbcDF.printSchema();
    }
}