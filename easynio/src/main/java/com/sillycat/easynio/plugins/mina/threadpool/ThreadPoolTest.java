package com.sillycat.easynio.plugins.mina.threadpool;

public class ThreadPoolTest {

	public static void main(String[] args) {
		ThreadPool pool = new ThreadPool(20);
		pool.addJob(new Worker("050"));
		pool.addJob(new Worker("job0110"));
		pool.addJob(new Worker("001job"));
	}

}
