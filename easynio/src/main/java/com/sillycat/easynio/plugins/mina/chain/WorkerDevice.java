package com.sillycat.easynio.plugins.mina.chain;

public class WorkerDevice implements IWorker {

	private IWorker next;

	public void handle(Iphone iphone) {
		iphone.setState(iphone.getState() + " Hardware Device is OK - ");
		if (next != null) {
			next.handle(iphone);
		}
	}

	public void setNextWorker(IWorker worker) {
		this.next = worker;
	}

}
