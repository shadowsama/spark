package com.shadow.jvm.concurent.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized在发生异常时，会自动释放线程占有的锁，因此不会导致死锁现象发生；而Lock在发生异常时，如果没有主动通过unLock()去释放锁，则很可能造成死锁现象，因此使用Lock时需要在finally块中释放锁；
 */
public class LockTest {
    private static ArrayList<Integer> arrayList = new ArrayList<Integer>();

    // 可重入锁
    static Lock lock = new ReentrantLock(); // 注意这个地方

    public static void main(String[] args) {


        Runnable r = () -> {
            Thread thread = Thread.currentThread();
            lock.lock();
            try {
                System.out.println(thread.getName() + "得到了锁");
                for (int i = 0; i < 5; i++) {
                    arrayList.add(i);
                }
            } catch (Exception e) {
            } finally {
                System.out.println(thread.getName() + "释放了锁");
                lock.unlock();
            }


        };

        /**
         *  lock 对代码块进行同步，finally中释放锁
         */
//        t1得到了锁
//                t1释放了锁
//        t2得到了锁
//                t2释放了锁
        new Thread(r,"t1").start();
        new Thread(r,"t2").start();
    }



}
