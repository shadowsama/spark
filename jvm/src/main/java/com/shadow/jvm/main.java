package com.shadow.jvm;

import java.io.*;
import java.sql.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by shadow on 2017/6/19 0019.
 */
public class main {


    public static void main(String[] args) throws IOException, SQLException {

        String  outPutPath ="D:\\1\\";
        String filename="1";


        LinkedHashMap<String, String> rowMapper = new LinkedHashMap<>();

        rowMapper.put("id","id");
        rowMapper.put("idcard","idcars");
        rowMapper.put("username","username");
        rowMapper.put("bankcardno","bankcardno");
        rowMapper.put("phone","phone");

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
                csvFileOutputStream.write(
                         propertyEntry.getValue().toString() );
                if (propertyIterator.hasNext()) {
                    csvFileOutputStream.write(",");
                }
            }
            csvFileOutputStream.newLine();



            String url = "jdbc:mysql://localhost:3306/test?user=root&password=root";
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            long allStart = System.currentTimeMillis();
            long count =0;

            Connection con = null;
            PreparedStatement ps = null;
            Statement st = null;
            ResultSet rs = null;

            try {
                con = DriverManager.getConnection(url);

                ps = (PreparedStatement) con.prepareStatement("SELECT  * from fbs_bank4",ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY);
                //   ps.setFetchSize(Integer.MIN_VALUE);
            //    ps.setFetchDirection(ResultSet.FETCH_REVERSE);

                rs = ps.executeQuery();
                int col = rs.getMetaData().getColumnCount();


                while (rs.next()) {
                    csvFileOutputStream.write(""
                            + rs.getInt("id") + "\t,");
                    csvFileOutputStream.write(""
                            + rs.getString("id_card") + "\t,");
                    csvFileOutputStream.write(""
                            + rs.getString("username") + "\t,");
                    csvFileOutputStream.write(""
                            + rs.getString("bank_card_no")+ "\t,");
                    csvFileOutputStream.write(""
                            + rs.getString("bank_phone") + "\t");
                    csvFileOutputStream.newLine();
                    csvFileOutputStream.flush();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(rs!=null){
                        rs.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    if(ps!=null){
                        ps.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    if(con!=null){
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
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

        ZipUtil.zip(outPutPath,outPutPath+filename+".zip");

    }

}
