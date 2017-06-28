package com.shadow.jvm.concurent.collection;

import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread) //默认状态。实例将分配给运行给定测试的每个线程。
@BenchmarkMode(Mode.Throughput) //吞吐量测试
@Fork(1)
@Threads(1)
@Warmup(iterations = 0)
@Measurement(iterations = 10,timeUnit = TimeUnit.SECONDS,time = 1)
public class benchMark {

    

    @Benchmark//   3019422165.439
    public void testHahMap(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("1","1");
    }
               //  3003721620.165
    @Benchmark//   3160296955.249 ops/s
    public void testConcurrentHahMap(){
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();

        concurrentHashMap.put("1","1");
    }



    @Benchmark//   34871996.788   16484439.789
    public void testHahMapPut(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("1","1");
        hashMap.put("2","2");
        hashMap.put("3","3");
        hashMap.put("4","4");

        hashMap.put("5","5");

    }

    @Benchmark//   20016156.733 ops/s 7447620.354
    public void testConcurrentHahMapPut(){
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();

        concurrentHashMap.put("1","1");
        concurrentHashMap.put("2","2");
        concurrentHashMap.put("3","3");
        concurrentHashMap.put("4","4");
        concurrentHashMap.put("5","5");
    }

    @Benchmark//   71027978.736
    public void testConcurrentHahMapget(){

        concurrentHashMap2.get("1");
        concurrentHashMap2.get("2");
        concurrentHashMap2.get("3");
        concurrentHashMap2.get("4");
        concurrentHashMap2.get("5");
    }

    @Benchmark// 73104677.068
    public void testHahMapget(){

        hashMap2.get("1");
        hashMap2.get("2");
        hashMap2.get("3");
        hashMap2.get("4");
        hashMap2.get("5");
    }

    ConcurrentHashMap<String, String> concurrentHashMap2;
    HashMap<String, String> hashMap2 ;


    @Setup
    public void init(){
        concurrentHashMap2  = new ConcurrentHashMap<>();

        concurrentHashMap2.put("1","1");
        concurrentHashMap2.put("2","2");
        concurrentHashMap2.put("3","3");
        concurrentHashMap2.put("4","4");
        concurrentHashMap2.put("5","5");


        hashMap2= new HashMap<>();

        hashMap2.put("1","1");
        hashMap2.put("2","2");
        hashMap2.put("3","3");
        hashMap2.put("4","4");

        hashMap2.put("5","5");

    }


}
