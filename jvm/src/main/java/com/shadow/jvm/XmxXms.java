package com.shadow.jvm;

import java.util.ArrayList;

/**
 * Xmx test
 * -Xmx256m -Xms256m
 * @throws 56
   Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
   at XmxXms.main(XmxXms.java:18)
 */
public class XmxXms {


    public static void main(String[] args) {
        Byte[][] bytes = new Byte[1024][1024];

        ArrayList<Byte[][]> bytes1 = new ArrayList<>();

        for (int i=0;i<Integer.MAX_VALUE;i++){
            bytes1.add(new Byte[1024][1024]);
            System.out.println(i);
        }

    }
}
