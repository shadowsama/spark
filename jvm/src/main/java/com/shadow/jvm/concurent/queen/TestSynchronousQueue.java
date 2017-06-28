package com.shadow.jvm.concurent.queen;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class TestSynchronousQueue {

    class Producer implements Runnable {
        private BlockingQueue<String> queue;
        List<String> objects = Arrays.asList("one", "two", "three");

        public Producer(BlockingQueue<String> q) {
            this.queue = q;
        }

        @Override
        public void run() {
            try {
                for (String s : objects) {
                    queue.put(s);// 产生数据放入队列中
                    System.out.printf("put:%s%n",s);
                }
                queue.put("Done");// 已完成的标志
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Consumer implements Runnable {
        private BlockingQueue<String> queue;

        public Consumer(BlockingQueue<String> q) {
            this.queue = q;
        }

        @Override
        public void run() {
            String obj = null;
            try {
                while (!((obj = queue.take()).equals("Done"))) {
                    System.out.println(obj);//从队列中读取对象
                    Thread.sleep(3000);     //故意sleep，证明Producer是put不进去的
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        BlockingQueue<String> q=new SynchronousQueue<String>();
        TestSynchronousQueue t=new TestSynchronousQueue();
        new Thread(t.new Producer(q)).start();
        new Thread(t.new Consumer(q)).start();
    }

}