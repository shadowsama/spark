/**
 * Copyright (c) 2016, 791650277@qq.com(Mr.kiwi) All Rights Reserved.
 */
package com.shadow.jvm;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目：liquidator-platform-openapi
 * 包名：com.fshows.liquidator.platform.openapi.common.utils
 * 功能：
 * 时间：2016-09-18 21:12
 * 作者：Mr.Kiwi
 */
public class CSVUtil {

    public static File createCSVFile(List exportData, LinkedHashMap rowMapper,
                                     String outPutPath, String filename) {

        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            csvFile = new File(outPutPath + filename + ".csv");
            // csvFile.getParentFile().mkdir();
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
            // 写入文件头部
            for (Iterator propertyIterator = rowMapper.entrySet().iterator(); propertyIterator.hasNext(); ) {
                Map.Entry propertyEntry = (Map.Entry) propertyIterator
                        .next();
                csvFileOutputStream.write("\""
                        + propertyEntry.getValue().toString() + "\"");
                if (propertyIterator.hasNext()) {
                    csvFileOutputStream.write(",");
                }
            }
            csvFileOutputStream.newLine();


            // 写入文件内容
            for (Iterator iterator = exportData.iterator(); iterator.hasNext(); ) {
                // Object row = (Object) iterator.next();
                LinkedHashMap row = (LinkedHashMap) iterator.next();

                for (Iterator propertyIterator = row.entrySet().iterator(); propertyIterator.hasNext(); ) {
                    Map.Entry propertyEntry = (Map.Entry) propertyIterator.next();
                    // System.out.println( BeanUtils.getProperty(row, propertyEntry.getKey().toString()));
                    csvFileOutputStream.write("\""
                            + propertyEntry.getValue().toString() + "\"");
                    if (propertyIterator.hasNext()) {
                        csvFileOutputStream.write(",");
                    }
                }
                if (iterator.hasNext()) {
                    csvFileOutputStream.newLine();
                }
            }
            csvFileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (csvFileOutputStream != null) {
                try {
                    csvFileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return csvFile;
    }

    public static void main(String[] args) {
    }

}
