package com.xinguang.esearch.test;

public class Number3 {
	public static void main(String[] args) {
		printString();
		System.out.println("==========");
		printString(new String[] { "我", "和", "你" });
		System.out.println("==========");
		printString("我", "和", "你");
	}

	public static void printString(String... str) {

		if (str.length == 0) {
			System.out.println("没有传参数。");
		}

		for (int i = 0; i < str.length; i++) {
			System.out.println(str[i]);
		}

	}
}