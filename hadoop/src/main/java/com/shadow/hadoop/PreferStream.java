package com.shadow.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Class: PreferStream
 * Author: wanghf
 * Date: 2017/7/9 0009  19:44
 * Descrption:
 */
public class PreferStream {

    FileSystem fs = null;
    Configuration conf = null;

    @Before
    public void init() throws Exception{

        System.setProperty("HADOOP_USER_NAME", "root");
        conf = new Configuration();
//		conf.set("fs.defaultFS", "hdfs://mini1:9000");
        conf.addResource("core-site.xml");
        conf.addResource("hdfs-site.xml");
       // conf.set("dfs.replication", "5");

        //拿到一个文件系统操作的客户端实例对象
        fs = FileSystem.get(conf);
        //可以直接传入 uri和用户身份
        //fs = FileSystem.get(new URI("hdfs://master:9000"),conf,"hadoop");
    }

    @Test
    public void testCat() throws IllegalArgumentException, IOException {

        FSDataInputStream in = fs.open(new Path("/20170709"));
        //拿到文件信息
        FileStatus[] listStatus = fs.listStatus(new Path("/20170709"));
        //获取这个文件的所有block的信息
        BlockLocation[] fileBlockLocations = fs.getFileBlockLocations(listStatus[0], 0L, listStatus[0].getLen());
        //第一个block的长度
        long length = fileBlockLocations[0].getLength();
        //第一个block的起始偏移量
        long offset = fileBlockLocations[0].getOffset();

        System.out.println(length);
        System.out.println(offset);

        //获取第一个block写入输出流
		IOUtils.copyBytes(in, System.out, (int)length);
        byte[] b = new byte[4096];

        FileOutputStream os = new FileOutputStream(new File("d:/block0"));
        while(in.read(offset, b, 0, 4096)!=-1){
            os.write(b);
            offset += 4096;
            if(offset>=length) return;
        };
        os.flush();
        os.close();
        in.close();
    }


    // 块的各种信息
    @Test
    public void testLs() throws Exception {

        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);

        while (listFiles.hasNext()) {
            LocatedFileStatus fileStatus = listFiles.next();
            System.out.println("blocksize: " + fileStatus.getBlockSize());
            System.out.println("owner: " + fileStatus.getOwner());
            System.out.println("Replication: " + fileStatus.getReplication());
            System.out.println("Permission: " + fileStatus.getPermission());
            System.out.println("Name: " + fileStatus.getPath().getName());
            System.out.println("------------------");
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            for (BlockLocation b : blockLocations) {
                System.out.println("块起始偏移量: " + b.getOffset());
                System.out.println("块长度:" + b.getLength());
                //块所在的datanode节点
                String[] datanodes = b.getHosts();
                for (String dn : datanodes) {
                    System.out.println("datanode:" + dn);
                }
            }

        }
    }





}
