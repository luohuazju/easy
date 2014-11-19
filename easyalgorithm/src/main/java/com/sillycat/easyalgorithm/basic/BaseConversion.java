package com.sillycat.easyalgorithm.basic;

public class BaseConversion {

	public static int convert10toRate(int source, int rate) {
		String result = "";
		while (source != 0) {
			result = source % rate + result;
			source = source / rate;
		}
		return result.isEmpty() ? 0 : Integer.valueOf(result);
	}

	public static void main(String[] args) {
		// convert 23(10) = 212(3)
		System.out.println("23(10) = " + convert10toRate(23, 3) + "(3)");
		// convert 0(10) = 0(3)
		System.out.println("0(10) = " + convert10toRate(0, 3) + "(3)");
		// convert 101(10) = 10202(3)
		System.out.println("101(10) = " + convert10toRate(101, 3) + "(3)");

		System.out.println("==============================");
		// convert 2(10) = 10(2)
		System.out.println("2(10) = " + convert10toRate(2, 2) + "(2)");
		// convert 0(10) = 0(2)
		System.out.println("0(10) = " + convert10toRate(0, 2) + "(2)");
		// convert 101(10) = 1100101(2)
		System.out.println("101(10) = " + convert10toRate(101, 2) + "(2)");
	}

}
