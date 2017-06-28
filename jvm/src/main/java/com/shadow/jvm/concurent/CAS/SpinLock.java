package com.shadow.jvm.concurent.CAS;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Class: SpinLock
 * Author: wanghf
 * Date: 2017/6/23 0023  10:21
 * Descrption:  线程只有在unlocked之后，下一个线程才能execute ，之前的线程都属于locked 自旋状态
 * **乐观锁其实就是不加锁，用CAS + 循环重试，实现多个线程/多个客户端，并发修改数据的问题。**
 * @See： https://coderbee.net/index.php/concurrent/20131115/577
 */
public class SpinLock {

//    private static final Unsafe unsafe = Unsafe.getUnsafe();

    private  volatile AtomicReference<Thread> sign =new AtomicReference<>();

    // 如果sign为空，则把当前线程自旋 ,否则继续执行线程
    public void lock(){
        Thread current = Thread.currentThread();
        while(!sign .compareAndSet(null, current)){
            System.out.println(current.getName()+" is spinning ,"+System.nanoTime());
        }
    }

    // 当前线程和期望线程一致，则执行该线程
    public void unlock (){
        Thread current = Thread.currentThread();
        System.out.println(current.getName()+" un locked,"+System.nanoTime());
        sign .compareAndSet(current, null);
    }

   static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) {

         final SpinLock spinLock = new SpinLock();


//
//        List<Thread> collect = IntStream.range(0, 10).mapToObj(i -> new Thread(() -> {
//            spinLock.lock();
//            System.out.println(Thread.currentThread().getName()+ " executed,"+System.nanoTime());
//            spinLock.unlock();
//        })).collect(Collectors.toList());

//        collect.forEach(thread -> threadPool.execute(thread));


        for (int i=0;i<10;i++){

            new Thread(()->{

                spinLock.lock();
                spinLock.unlock();
            System.out.println(Thread.currentThread().getName()+ " executed,"+System.nanoTime());

            }).start();

        }


    }
}