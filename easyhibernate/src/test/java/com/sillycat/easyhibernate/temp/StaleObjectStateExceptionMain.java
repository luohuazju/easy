package com.sillycat.easyhibernate.temp;

public class StaleObjectStateExceptionMain {

	public static void main(String[] args) {
		for(int i = 0;i< 10;i++){
			Thread t1 = new Thread(new MyRunnable("1"));
			Thread t2 = new Thread(new MyRunnable("1"));
			t1.start();
		    t2.start();
		}
	}

}
