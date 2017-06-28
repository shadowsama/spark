package com.shadow.jvm.concurent.queen;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TestDelayQueue {

    private class Stadium implements Delayed
    {
        long trigger;

        public Stadium(long i){
            trigger=System.currentTimeMillis()+i;
        }

        @Override
        public long getDelay(TimeUnit arg0) {
            long n=trigger-System.currentTimeMillis();
            return n;
        }

        @Override
        public int compareTo(Delayed arg0) {
            return (int)(this.getDelay(TimeUnit.MILLISECONDS)-arg0.getDelay(TimeUnit.MILLISECONDS));
        }

        public long getTriggerTime(){
            return trigger;
        }

    }
    public static void main(String[] args)throws Exception {
        Random random=new Random();
        DelayQueue<Stadium> queue=new DelayQueue<Stadium>();
        TestDelayQueue t=new TestDelayQueue();

        for(int i=0;i<5;i++){
            queue.add(t.new Stadium(random.nextInt(30000)));
        }
        Thread.sleep(2000);

        while(true){
            Stadium s=queue.take();//延时时间未到就一直等待
            if(s!=null){
                System.out.println(System.currentTimeMillis()-s.getTriggerTime());//基本上是等于0
            }
            if(queue.size()==0)
                break;
        }
    }
}