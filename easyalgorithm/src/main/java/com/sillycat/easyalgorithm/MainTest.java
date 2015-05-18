package com.sillycat.easyalgorithm;

import java.util.ArrayList;

public class MainTest {

	public int solution(int A, int B, int N) {
		int sum = 0;
		int left = A%1000000007;
		int right = B%1000000007;
		if(N == 0)
			return left;
		if(N == 1)
			return right;
		for(int i = 2 ; i <= N; i++) {
			sum = (left + right)%1000000007;
			if(i%2 == 0) 
				left = sum;
			else
				right = sum;
		}
		return sum;
	}
	public static void main(String args[]) {
		MainTest s = new MainTest();
		System.out.println(s.solution(3,4,5));
	}
}
