package com.shadow.jvm.concurent;


import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Author: wanghf
 * createTime: 2017-06-03 8:43
 * desc: strat， 线程异步启动，顺序不定，哪个线程抢到CPU时间片，解先执行
 *       run， 同步，等线程启动玩，接着执行后面的程序
 */
public class ThreadFirst  {


    public static void main(String[] args) throws InterruptedException {

        System.out.println("Int Stream ");
        IntStream.range(0,10).forEach( i -> {System.out.print(i);});
        System.out.println();
        System.out.println("start 线程启动顺序交给CPU处理，异步处理");
        IntStream.range(0,10).forEach(i ->{new myThread(i).start();});


        TimeUnit.SECONDS.sleep(1);

        System.out.println();
        System.out.println("start 线程启动顺序交给CPU处理，同步处理");
        IntStream.range(0,10).forEach(i ->{new myThread(i).run();});
    }

       static   class myThread extends  Thread{

        int i;

        public myThread(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.out.print(i);
        }
    }

}
