package com.sillycat.easyalgorithm;

import java.util.ArrayList;

public class MainTest2 {

	class Solution {
	    public int solution(int[] array) {
	        // write your code in Java SE 8
	        // the arithmetic sequences will be
	        // 0, 1, 2
	        // 2, 3, 4
	        // 4, 5, 6
	        // 4, 5, 6, 7
	        // 5, 6, 7
	        ArrayList<Integer> tmpList = new ArrayList<Integer>();
			// length of the array
			int result = 0;
			int len = array.length;
			for (int i = 0; i < len - 1; i++) {
					tmpList.clear();
					tmpList.add(i);
					tmpList.add(i+1);
					int num = 2;
					int progression = array[i+1] - array[i];
					int current = i+1;
					int next = i + 2;
					while (next < len) {
						/* same progression */
						if (array[next] - array[current] == progression) {
							tmpList.add(next);
							current = next;
							num++;
					        if (num >= 3) {
					            System.out.println("debug message :" + tmpList);
						        result = result + 1;
					        }
						}else{
							break;
						}
						next++;
					}
			}
			return result;
	    }
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
