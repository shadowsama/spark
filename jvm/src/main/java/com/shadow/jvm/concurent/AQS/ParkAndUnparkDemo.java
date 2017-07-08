package com.shadow.jvm.concurent.AQS;


import java.util.concurrent.locks.LockSupport;

/**
 * @see http://www.cnblogs.com/leesf456/p/5347293.html
 */
class MyThread extends Thread {
    private Object object;

    public MyThread(Object object) {
        this.object = object;
    }

    public void run() {
        System.out.println("before unpark");

        // 获取blocker
        System.out.println("Blocker info " + LockSupport.getBlocker((Thread) object));
        // 释放许可
        LockSupport.unpark((Thread) object);
        // 再次获取blocker
        System.out.println("Blocker info " + LockSupport.getBlocker((Thread) object));
        System.out.println("after unpark");
    }
}

public class ParkAndUnparkDemo {
    public static void main(String[] args) {
        MyThread myThread = new MyThread(Thread.currentThread());
        myThread.start();
        System.out.println("before park");
        // 获取许可
        LockSupport.park("ParkAndUnparkDemo");
        System.out.println("after park");
    }
}