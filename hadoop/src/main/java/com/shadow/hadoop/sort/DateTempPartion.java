package com.shadow.hadoop.sort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;


/**
 * Class: DateTempPartion
 * Author: wanghf
 * Date: 2017/7/11 0011
 * Descrption: 自定义排序器
 */
public class DateTempPartion extends Partitioner<DateTempPair, Text> {


    @Override
    public int getPartition(DateTempPair pair, Text text, int numOfPartion) {

        // 分区非负数
        return Math.abs(pair.getYearMonth().hashCode() % numOfPartion);
    }
}
