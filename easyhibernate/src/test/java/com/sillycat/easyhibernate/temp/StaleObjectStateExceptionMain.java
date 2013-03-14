package com.sillycat.easyhibernate.temp;

public class StaleObjectStateExceptionMain {

	public static void main(String[] args) {
		for(int i = 0;i< 2;i++){
			Thread t = new Thread(new MyRunnable("1"));
		    t.start();
		}
	}

}
