package com.shadow.jvm.concurent.executorFrameWork;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public interface ExecutorService extends Executor {

    void shutdown();
    boolean isShutdown();
    boolean isTerminated();
    boolean awaitTermination(long timeout, TimeUnit unit)
            throws InterruptedException;

}
