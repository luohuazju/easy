package com.sillycat.easynio.plugins.mina.threadpool;

public class Worker implements Runnable {

	private String cardNumber;

	public Worker(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public void run() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Thread: " + Thread.currentThread().getName()
				+ " do the job " + cardNumber);
	}

}
