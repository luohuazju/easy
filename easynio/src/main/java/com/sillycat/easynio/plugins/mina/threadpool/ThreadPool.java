package com.sillycat.easynio.plugins.mina.threadpool;

import java.util.LinkedList;

public class ThreadPool {

	private final int nThreads;

	private final PoolWorker[] threads;

	private final LinkedList<Runnable> queue;

	public ThreadPool(int nThreads) {
		this.nThreads = nThreads;
		queue = new LinkedList<Runnable>();
		threads = new PoolWorker[nThreads];
		for (int i = 0; i < nThreads; i++) {
			threads[i] = new PoolWorker();
			threads[i].start();
		}
	}

	public void addJob(Runnable r) {
		synchronized (queue) {
			queue.addLast(r);
			queue.notify();
		}
	}

	private class PoolWorker extends Thread {
		public void run() {
			Runnable r;
			while (true) {
				synchronized (queue) {
					while (queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException ignored) {
						}
					}
					r = queue.removeFirst();
				}
				try {
					r.run();
				} catch (RuntimeException e) {
				}
			}
		}
	}

	public int getnThreads() {
		return nThreads;
	}

}
