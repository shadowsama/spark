package com.shadow.jvm.concurent.exceutors;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/6/13 0013.
 */
public class ExecutorTest {


    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int workerCountOf(int c)  { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }


    public static void main(String[] args) {


        //00011111 11111111 11111111 11111111
        System.out.println(COUNT_BITS);

        System.out.println(CAPACITY);

        //100000 00000000 00000000 00000000 -1  29个0

        System.out.println( 0x20000000-1);

        System.out.println(Integer.toBinaryString(RUNNING));
        System.out.println(Integer.toBinaryString(SHUTDOWN));
        System.out.println(Integer.toBinaryString(STOP)); //running +1
        System.out.println(Integer.toBinaryString(TIDYING)); //running*2

    }

}
