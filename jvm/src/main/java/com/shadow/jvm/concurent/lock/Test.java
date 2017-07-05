package com.shadow.jvm.concurent.lock;

public class Test {
	public static void main(String[] args) {
		int num = Runtime.getRuntime().availableProcessors();
		System.out.println(num);
	}

}
