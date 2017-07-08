package com.shadow.jvm.concurent.executorFrameWork;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by shadow on 2017/7/6 0006.
 */
public class MyExecutorTest {


    public static void main(String[] args) throws Exception {
        try {
            ThreadPoolExecutor threadPool = Executors.fixedThreadPool(10);
            IntStream.rangeClosed(0, 100).forEach((i)-> {

                threadPool.execute(()->{

                    try {
                        Thread.sleep(new Random().nextInt(3));
                        System.out.println(Thread.currentThread().getName());

                    } catch (InterruptedException e) {
                    }

                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
