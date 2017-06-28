package com.shadow.jvm.concurent.AQS;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;


/**
 *@See  http://zhanjindong.com/2015/03/15/java-concurrent-package-aqs-AbstractQueuedSynchronizer
 * jucp 线程阻塞队列的维护 线程阻塞和唤醒
 */
class SimpleLock extends AbstractQueuedSynchronizer {
    private static final long serialVersionUID = -7316320116933187634L;

    public SimpleLock() {

    }

    protected boolean tryAcquire(int unused) {
        if (compareAndSetState(0, 1)) {
            setExclusiveOwnerThread(Thread.currentThread());
            return true;
        }
        return false;
    }

    protected boolean tryRelease(int unused) {
        setExclusiveOwnerThread(null);
        setState(0);
        return true;
    }

    public void lock() {
        acquire(1);
    }

    public boolean tryLock() {
        return tryAcquire(1);
    }

    public void unlock() {
        release(1);
    }

    public boolean isLocked() {
        return isHeldExclusively();
    }

    public static void main(String[] args) throws InterruptedException {
        final SimpleLock lock = new SimpleLock();
        // 主线程获取锁
        lock.lock();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //线程i获取锁 调用
                    /**
                     *   public final void acquire(int arg) {
                        if (!tryAcquire(arg) &&
                        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
                        selfInterrupt();
                     }
                     */
                    lock.lock();
                    System.out.println(Thread.currentThread().getId() + " acquired the lock!");
                    //线程i释放锁
                    lock.unlock();
                }
            }).start();
            // 简单的让线程按照for循环的顺序阻塞在lock上
            Thread.sleep(100);
        }

        System.out.println("main thread unlock!");

        //主线程释放锁
        lock.unlock();
    }

}