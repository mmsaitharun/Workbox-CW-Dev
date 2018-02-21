package com.incture.pmc.util;

public class SequenceGenerator {

	volatile static int n = 1;

	public static synchronized int nextNum() {
		return n++;
	}
}
