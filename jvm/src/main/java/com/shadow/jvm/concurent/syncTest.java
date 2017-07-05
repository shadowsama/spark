package com.shadow.jvm.concurent;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread) //默认状态。实例将分配给运行给定测试的每个线程。
@BenchmarkMode(Mode.Throughput) //吞吐量测试
@Fork(1)
@Threads(1)
@Warmup(iterations = 0)
@Measurement(iterations = 1, timeUnit = TimeUnit.SECONDS, time = 10)
public class syncTest {


    MyThread myThread;

    static class MyThread implements Runnable {
        private int count = 0;
        @Override
        public void run() {
            count++;
            System.out.println(Thread.currentThread().getName() + " >> " + count);
        }
    }


    @Setup
    public void setup(){
        myThread = new MyThread();
    }

    @Benchmark
   // @Test
    public void test() {

//        结果中出现 表明线程不安全

//        Thread-974 >> 1143
//        Thread-973 >> 1143

        new Thread(myThread).start();

    }
}
