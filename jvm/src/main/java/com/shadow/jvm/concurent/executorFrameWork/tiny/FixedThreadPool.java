package com.shadow.jvm.concurent.executorFrameWork.tiny;

import com.shadow.jvm.concurent.executorFrameWork.Executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/** 
 * Class: FixedThreadPool
 * Author: wanghf
 * Date: 2017/7/9 0006  10:22
 * Descrption: 简易版线程池
 */
public class FixedThreadPool implements Executor {

	private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    private ThreadGroup threadGroup = new ThreadGroup("FixedThreadPool");
	private volatile boolean isClosed = false;


	/**
	 * Class: FixedThreadPool
	 * Author: wanghf
	 * Date: 2017/7/8 0006  10:12
	 * Descrption: 初始化线程池
	 */
	public FixedThreadPool(int size) {
		threadGroup.setDaemon(true);
		for (int i = 0; i < size; i++) {
			new Thread(threadGroup, new Runnable() {
				@Override
				// 从队列中取出线程，同步执行
				public void run() {
					while (!isClosed || !workQueue.isEmpty()) {
						try {
							workQueue.take().run();
						} catch (InterruptedException e) {
							return;
						}
					}
				}
			}, "Thread-" + i).start();
		}
	}

	@Override
	public void execute(Runnable task) {
		if (isClosed) {
			throw new IllegalStateException("Threadpool already closed.");
		}
		workQueue.add(task);
	}

	public void finish() {
		isClosed = true;
		// Take active threads from thread group
		Thread[] threads = new Thread[threadGroup.activeCount()];
		int count = threadGroup.enumerate(threads);

		for (int i = 0; i < count; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		threadGroup.interrupt();
	}

	public void close() {
		if (!isClosed) {
			isClosed = true;
			workQueue.clear();
			threadGroup.interrupt();
		}
	}

}
