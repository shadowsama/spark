package com.shadow.jvm;

import java.sql.*;

/**
 * Created by shadow on 2017/6/17 0017.
 */
public class mysqlOOM {

    public static long importData(String sql){
        String url = "jdbc:mysql://:3306/test?user=username&password=password";
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

            ps = (PreparedStatement) con.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);

            ps.setFetchSize(Integer.MIN_VALUE);

            ps.setFetchDirection(ResultSet.FETCH_REVERSE);

            rs = ps.executeQuery();


            while (rs.next()) {

                //此处处理业务逻辑
                count++;
                if(count%600000==0){
                    System.out.println(" 写入到第  "+(count/600000)+" 个文件中！");
                    long end = System.currentTimeMillis();
                }

            }
            System.out.println("取回数据量为  "+count+" 行！");
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
        return count;

    }

    public static void main(String[] args) throws InterruptedException {

        String sql = "select * from test.bigTable ";
        importData(sql);

    }

}
