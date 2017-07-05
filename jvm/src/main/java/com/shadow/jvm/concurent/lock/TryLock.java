package com.shadow.jvm.concurent.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Class: MyTryLock
 * Author: wanghf
 * Date: 2017/7/5 0005  21:58
 * Descrption:  一个线程获取不到锁，则中断结果 ，结果将不确定
 */
public class TryLock {

	private static ArrayList<Integer> arrayList = new ArrayList<Integer>();
	static Lock lock = new ReentrantLock(); // 注意这个地方
	public static void main(String[] args) {
		
		Runnable r=() -> {
			{
				Thread thread = Thread.currentThread();
				boolean tryLock = lock.tryLock();
				System.out.println(thread.getName() + " " + tryLock);
				if (tryLock) {
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
				}
			}
		};

		new Thread(r,"t1").start();
		new Thread(r,"t2").start();

	}


}
