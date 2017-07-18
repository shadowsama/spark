#!/usr/bin/env bash

$HADOOP_HOME/bin/hdfs dfs -mkdir /topN
$HADOOP_HOME/bin/hdfs dfs -put topN/ /topN

export JAVA_HOME=/usr/java/jdk1.8.0_131
#
INPUT=$MP/top10data.txt
prog=org.dataalgorithms.chap03.spark.Top10
$SPARK_HOME/bin/spark-submit --class com.shadow.spark.topN.TopN
  --master yarn-cluster
    spark-1.0-SNAPSHOT.jar
    /topN/topN


