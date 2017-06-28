package com.shadow.jvm.concurent.exceutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;


/**
 * Author: wanghf
 * createTime: 2017-06-10 17:47
 * desc: 任务之间不独立，使用无界队列，底层使用SynchronousQueue<Runnable>(),接受一个任务，必须有一个线程去运行，所以是无界嘟列
 */
public class Pool {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        // 通过jvisualivm 观察 发现60 s 后  没有新任务线程池全部清空
        IntStream.rangeClosed(0,10).forEach(

                (i)->  threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName() +":" );
                }));

//        Thread.sleep(60000);
//
//
//        IntStream.rangeClosed(0,100).forEach(
//
//                (i)->  threadPool.execute(()->{
//
//
//
//                    System.out.println(Thread.currentThread().getName() +":" );
//                }));
//
//        Thread.sleep(60000);
//        IntStream.rangeClosed(0,1).forEach(
//
//                (i)->  threadPool.execute(()->{
//
//
//
//                    System.out.println(Thread.currentThread().getName() +":" );
//                }));
//
//        Thread.sleep(600000);
    }

}
