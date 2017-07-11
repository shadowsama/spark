package com.shadow.hadoop.sort;


import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

/**
 * Class: Main
 * Author: wanghf
 * Date: 2017/7/11 0011  22:07
 * Descrption:  driver 驱动程序
 */
@Slf4j
public class Main  extends Configured implements Tool {


    // 设置输入，输出类型
    static class secondrySortMapper extends Mapper<LongWritable ,Text,DateTempPair,Text>{

        private final Text theTemperature = new Text();
        private final DateTempPair pair = new DateTempPair();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] splits = value.toString().split(",");

            // YYYY = tokens[0]
            // MM = tokens[1]
            // DD = tokens[2]
            // temperature = tokens[3]
            String yearMonth = splits[0] + splits[1];
            String day = splits[2];
            int temperature = Integer.parseInt(splits[3]);

            pair.setYearMonth(yearMonth);
            pair.setDay(day);
            pair.setTemperature(temperature);
            theTemperature.set(splits[3]);

            // emit ...
            context.write(pair, theTemperature);


        }
    }

    static class secondrySortReducer extends Reducer<DateTempPair, Text, Text, Text>{

        @Override
        protected void reduce(DateTempPair key, Iterable<Text> values, Context context) throws IOException, InterruptedException {


            StringBuilder builder = new StringBuilder();
            for (Text value : values) {
                builder.append(value.toString());
                builder.append(",");
            }
            context.write(key.getYearMonth(), new Text(builder.toString()));

        }
    }


    @Override
    public int run(String[] args) throws Exception {

        Configuration conf = getConf();
        Job job = new Job(conf);
        job.setJarByClass(Main.class);
        job.setJobName("SecondarySortDriver");

        // args[0] = input directory
        // args[1] = output directory
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setOutputKeyClass(DateTempPair.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(secondrySortMapper.class);
        job.setReducerClass(secondrySortReducer.class);
        job.setPartitionerClass(DateTempPartion.class);
        job.setGroupingComparatorClass(DateTempGroupingCompartor.class);

        boolean status = job.waitForCompletion(true);
        log.info("run(): status="+status);
        return status ? 0 : 1;
    }


    public static void main(String[] args) throws Exception {

        // Make sure there are exactly 2 parameters
        if (args.length != 2) {
          log.warn("SecondarySortDriver <input-dir> <output-dir>");
            throw new IllegalArgumentException("SecondarySortDriver <input-dir> <output-dir>");
        }

        //String inputDir = args[0];
        //String outputDir = args[1];
        int returnStatus = submitJob(args);
        log.info("returnStatus="+returnStatus);

        System.exit(returnStatus);



    }


    public static int submitJob(String[] args) throws Exception {
        //String[] args = new String[2];
        //args[0] = inputDir;
        //args[1] = outputDir;
        int returnStatus = ToolRunner.run(new Main(), args);
        return returnStatus;
    }




}
