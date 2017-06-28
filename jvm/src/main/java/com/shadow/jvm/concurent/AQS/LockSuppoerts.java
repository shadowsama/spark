package com.shadow.jvm.concurent.AQS;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by Administrator on 2017/6/13 0013.
 */
public class LockSuppoerts {
    public static void main(String[] args) throws InterruptedException {
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                LockSupport.park();
                System.out.println("thread " + Thread.currentThread().getId() + " awake!");
            }
        });

        t.start();
        Thread.sleep(3000);

        // 2. 中断
        t.interrupt();
    }
}
