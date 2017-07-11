package com.shadow.hadoop.sort;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.codehaus.jackson.annotate.JsonSubTypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Class: DateTempPair
 * Author: wanghf
 * Date: 2017/7/11 0011  21:23
 * Descrption: 自定义pojo实现Writeable
 */
//@Getter
//@Setter
public class DateTempPair  implements Writable, WritableComparable<DateTempPair> {

    private final Text yearMonth = new Text();
    private final Text day = new Text();
    private final IntWritable temperature = new IntWritable();


    // 自定义排序方式 降序
    @Override
    public int compareTo(DateTempPair other) {

        int compareValue = this.yearMonth.compareTo(other.getYearMonth());
        if (compareValue == 0) {
            compareValue = temperature.compareTo(other.getTemperature());
        }
        return compareValue;
    }

    // 自定义序列化方法
    @Override
    public void write(DataOutput out) throws IOException {
        yearMonth.write(out);
        day.write(out);
        temperature.write(out);
    }

    // 自定义对象反序列化方法
    @Override
    public void readFields(DataInput dataInput) throws IOException {

        yearMonth.readFields(dataInput);
        day.readFields(dataInput);
        temperature.readFields(dataInput);
    }

    public Text getYearMonthDay() {
        return new Text(yearMonth.toString()+day.toString());
    }

    public Text getYearMonth() {
        return yearMonth;
    }

    public Text getDay() {
        return day;
    }

    public IntWritable getTemperature() {
        return temperature;
    }

    public void setYearMonth(String yearMonthAsString) {
        yearMonth.set(yearMonthAsString);
    }

    public void setDay(String dayAsString) {
        day.set(dayAsString);
    }

    public void setTemperature(int temp) {
        temperature.set(temp);
    }

}
