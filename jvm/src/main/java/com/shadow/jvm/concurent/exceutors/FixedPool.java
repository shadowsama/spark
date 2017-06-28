package com.shadow.jvm.concurent.exceutors;


import java.util.Arrays;

import java.util.List;
import java.util.concurrent.*;

import static java.util.stream.Collectors.toList;

/**
 * Author: wanghf
 * createTime: 2017-06-10 20:11
 * desc:  fix的线程池没有设置超时时间，必须shutdown
 */
public class FixedPool {

    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newFixedThreadPool(10);


//        IntStream.rangeClosed(0,100).forEach((i)->{
//            threadPool.execute(()->{
//                System.out.println(Thread.currentThread().getName());
//            });
//
//
//        });

        List<Future> collect = Arrays.asList("1", "2", "3").
                stream().
                map((i) ->  threadPool.submit(new task(Integer.parseInt(i))))
                .collect(toList());


        collect.stream().forEach(future -> {
            try {
                Integer integer = (Integer) future.get(2, TimeUnit.SECONDS);
                System.out.println(integer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        });

    }


}
class task implements Callable<Integer> {

    int i;

    public task(int i) {
        this.i = i;
    }

    @Override
    public Integer call() throws Exception {
        return i;
    }
}
