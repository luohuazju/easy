package com.sillycat.easynio.plugins.mina.chain;

public class WorkerIntegration implements IWorker {

	private IWorker next;

	public void handle(Iphone iphone) {
		iphone.setState(iphone.getState() + " Integration testing is done - ");
		if (next != null) {
			next.handle(iphone);
		}
	}

	public void setNextWorker(IWorker worker) {
		this.next = worker;
	}

}
