package com.shadow.jvm.concurent.executorFrameWork;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolExecutor {

    //ctl一个变量同时存储runState和workerCount，其中runState占用高3位，workCount占用低29位
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    //workerCount使用的位数：32-3=29位
    private static final int COUNT_BITS = Integer.SIZE - 3;
    //workerCount最大值:536870911,即0b00011111_11111111_11111111_11111111
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;
    // runState存储在高位，占用3位
    //0b11100000_00000000_00000000_00000000
    private static final int RUNNING = -1 << COUNT_BITS;
    //0b00000000_00000000_00000000_00000000
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    //0b00100000_00000000_00000000_00000000
    private static final int STOP = 1 << COUNT_BITS;
    //0b01000000_00000000_00000000_00000000
    private static final int TIDYING = 2 << COUNT_BITS;
    //0b01100000_00000000_00000000_00000000
    private static final int TERMINATED = 3 << COUNT_BITS;

    // 获取runState，即保留ctl的高3位，后29位置0
    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    //获取workerCount，即保留ctl的低29位，高3位置0
    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    //设置ctl,或操作
    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    // 阻塞队列表示 存放待运行的线程
    private final BlockingQueue<Runnable> workQueue;

    private final ReentrantLock mainLock = new ReentrantLock();

    private final Condition termination = mainLock.newCondition();

    private int largestPoolSize;

    private long completedTaskCount;

    private volatile long keepAliveTime;

    private volatile int corePoolSize;

    private volatile int maximumPoolSize;

    private volatile ThreadFactory threadFactory;

    //原子操作，增加正在工作的线程数量
    private boolean compareAndIncrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect + 1);
    }

    // 最高位为1,即c是负数就表线程池正在运行
    private static boolean isRunning(int c) {
        return c < SHUTDOWN;
    }

    private boolean compareAndDecrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect - 1);
    }

    private void decrementWorkerCount() {
        do {
        } while (!compareAndDecrementWorkerCount(ctl.get()));
    }

    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory
                              ) {
        if (corePoolSize < 0 ||
                maximumPoolSize <= 0 ||
                maximumPoolSize < corePoolSize ||
                keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null)
            throw new NullPointerException();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory=threadFactory;
    }


    public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();
        /**  step1: 判断当前线程数 >= corePoolSize。如果小于，新建线程执行；如果大于，进入step2
         #   step2: 判断队列是否已满。未满，放入；已满，进入step3
         #   step3: 判断当前线程数 >= maxPoolSize。如果小于，新建线程执行；如果大于，进入step4
         #   step4: 根据拒绝策略，拒绝任务 ignore
         */
        // 当前的运行状态及线程数量
        int c = ctl.get();
        //  直接启动新的线程。第二个参数true:addWorker中会重新检查workerCount是否小于corePoolSize
        if (workerCountOf(c) < corePoolSize) {
            if (addWorker(command, true))
                return;
            c = ctl.get();
        }
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            if (workerCountOf(recheck) == 0)
                addWorker(null, false);
        }

    }

    private boolean addWorker(Runnable firstTask, boolean core) {
        retry:
        for (; ; ) {
            int c = ctl.get();
            int rs = runStateOf(c);

            // Check if queue empty only if necessary.
            if (rs >= SHUTDOWN &&
                    !(rs == SHUTDOWN &&
                            firstTask == null &&
                            !workQueue.isEmpty()))
                return false;

            for (; ; ) {
                int wc = workerCountOf(c);
                if (wc >= CAPACITY ||
                        wc >= (core ? corePoolSize : maximumPoolSize))
                    return false;
                if (compareAndIncrementWorkerCount(c))
                    break retry;
                c = ctl.get();  // Re-read ctl
                if (runStateOf(c) != rs)
                    continue retry;
                // else CAS failed due to workerCount change; retry inner loop
            }


        }
        boolean workerStarted = false;
        boolean workerAdded = false;
       Worker w = null;
        try {
            w = new Worker(firstTask);
            final Thread t = w.thread;
            if (t != null) {
                final ReentrantLock mainLock = this.mainLock;
                mainLock.lock();
                try {
                    // Recheck while holding lock.
                    // Back out on ThreadFactory failure or if
                    // shut down before lock acquired.
                    int rs = runStateOf(ctl.get());

                    if (rs < SHUTDOWN ||
                            (rs == SHUTDOWN && firstTask == null)) {
                        if (t.isAlive()) // precheck that t is startable
                            throw new IllegalThreadStateException();
                        workerAdded = true;
                    }
                } finally {
                    mainLock.unlock();
                }
                if (workerAdded) {
                    t.start();
                    workerStarted = true;
                }
            }
        } finally {

        }
        return workerStarted;
    }


    private final class Worker
            extends AbstractQueuedSynchronizer
            implements Runnable {

        private static final long serialVersionUID = 6138294804551838833L;

        final Thread thread;
        Runnable firstTask;
        volatile long completedTasks;

        Worker(Runnable firstTask) {
            setState(-1); // inhibit interrupts until runWorker
            this.firstTask = firstTask;
            this.thread = getThreadFactory().newThread(this);
        }

        /**
         * Delegates main run loop to outer runWorker
         */
        public void run() {
            runWorker(this);
        }

        // Lock methods
        //
        // The value 0 represents the unlocked state.
        // The value 1 represents the locked state.

        protected boolean isHeldExclusively() {
            return getState() != 0;
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

        void interruptIfStarted() {
            Thread t;
            if (getState() >= 0 && (t = thread) != null && !t.isInterrupted()) {
                try {
                    t.interrupt();
                } catch (SecurityException ignore) {
                }
            }
        }
    }

    public static ThreadFactory defaultThreadFactory() {
        return new DefaultThreadFactory();
    }

    static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    public ThreadFactory getThreadFactory() {
        return threadFactory;
    }

//    第一次启动会执行初始化传进来的任务firstTask；
//    然后会从workQueue中取任务执行，如果队列为空则等待keepAliveTime这么长时间
    final void runWorker(Worker w) {
        Thread wt = Thread.currentThread();
        Runnable task = w.firstTask;
        w.firstTask = null;
        w.unlock(); // allow interrupts
        boolean completedAbruptly = true;
        try {
            while (task != null || (task = getTask()) != null) {
                w.lock();
                // If pool is stopping, ensure thread is interrupted;
                // if not, ensure thread is not interrupted.  This
                // requires a recheck in second case to deal with
                // shutdownNow race while clearing interrupt
                if (false)
                    wt.interrupt();
                try {
                    Throwable thrown = null;
                    try {
                        task.run();
                    } catch (RuntimeException x) {
                        thrown = x;
                        throw x;
                    } catch (Error x) {
                        thrown = x;
                        throw x;
                    } catch (Throwable x) {
                        thrown = x;
                        throw new Error(x);
                    } finally {
                    }
                } finally {
                    task = null;
                    w.completedTasks++;
                    w.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
        }
    }

    private Runnable getTask() {
        boolean timedOut = false; // Did the last poll() time out?

        for (; ; ) {
            int c = ctl.get();
            int rs = runStateOf(c);

            // Check if queue empty only if necessary.
            if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
                decrementWorkerCount();
                return null;
            }

            int wc = workerCountOf(c);

            // Are workers subject to culling?
            boolean timed = true;
            //allowCoreThreadTimeOut || wc > corePoolSize;

            if ((wc > maximumPoolSize || (timed && timedOut))
                    && (wc > 1 || workQueue.isEmpty())) {
                if (compareAndDecrementWorkerCount(c))
                    return null;
                continue;
            }

            try {
                Runnable r = timed ?
                        workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
                        workQueue.take();
                if (r != null)
                    return r;
                timedOut = true;
            } catch (InterruptedException retry) {
                timedOut = false;
            }
        }
    }

}
