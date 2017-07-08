package com.shadow.jvm.concurent.executorFrameWork;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Class: Executoes
 * Author: wanghf
 * Date: 2017/7/5 0005  23:34
 * Descrption:
 */
public class Executors {




    public  static  ThreadPoolExecutor fixedThreadPool(int thread){

        return new ThreadPoolExecutor(thread,thread,    0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadPoolExecutor.DefaultThreadFactory());
    }



}
