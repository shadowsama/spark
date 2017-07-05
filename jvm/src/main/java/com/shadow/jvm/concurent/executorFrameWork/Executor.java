package com.shadow.jvm.concurent.executorFrameWork;

import java.util.concurrent.RejectedExecutionException;


/**
 * Class: Executor
 * Author: wanghf
 * Date: 2017/7/5 0005  23:29
 * Descrption:
 */
public interface Executor {
    void execute(Runnable command);
}