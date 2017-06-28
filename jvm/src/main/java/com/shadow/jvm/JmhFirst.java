package com.shadow.jvm;

import org.openjdk.jmh.annotations.*;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


@State(Scope.Thread) //默认状态。实例将分配给运行给定测试的每个线程。
@BenchmarkMode(Mode.Throughput) //吞吐量测试
//@BenchmarkMode(Mode.SampleTime)
//@BenchmarkMode(Mode.SingleShotTime)
//@BenchmarkMode(Mode.AverageTime)
@Fork(2)
@Threads(1)
@Warmup(iterations = 0)
@Measurement(iterations = 1,timeUnit = TimeUnit.SECONDS,time = 10)
public class JmhFirst {


    private ExecutorService executorService;

    private AtomicInteger atomicInteger;

    private int i;
    private int a[] = new int[5];

    @Setup
    public void setUp()
    {
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.atomicInteger = new AtomicInteger();
        i=0;
    }

    @TearDown
    public void tearDown() throws InterruptedException
    {
        System.out.println(">>  "+atomicInteger);
        this.executorService.shutdownNow();
        this.executorService.awaitTermination(1L, TimeUnit.SECONDS);
    }
    @Benchmark
    public void aVoid() throws InterruptedException {

        Thread.sleep(2);
        System.out.println(new Date().toString()+ Thread.currentThread().getName()+ atomicInteger.getAndAdd(1));;
    }



    @Benchmark

    public void intAdd() {
        System.out.println(i++);
    }
    private static final int SIZE = 1000000;
    public final List<Integer> integersJDK = new ArrayList<>(SIZE);

}