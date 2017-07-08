package com.shadow.jvm.concurent.executorFrameWork.tiny;

import org.junit.Test;


public class ThreadPoolTest {


	@Test
	public void testExecute() {
		FixedThreadPool pool = new FixedThreadPool(4);
		for (int i = 0; i < 12; i++) {
			pool.execute(()->{

				String name = Thread.currentThread().getName();
				System.out.println(name);
			});
		}
		pool.finish();
		pool.close();

	}


}
