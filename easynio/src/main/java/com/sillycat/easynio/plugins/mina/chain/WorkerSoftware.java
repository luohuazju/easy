package com.sillycat.easynio.plugins.mina.chain;

public class WorkerSoftware implements IWorker{

	private IWorker next;
	
	public void handle(Iphone iphone) {
		iphone.setState(iphone.getState() + " Software and IOS are ready - ");
		if (next != null) {
			next.handle(iphone);
		}
	}

	public void setNextWorker(IWorker worker) {
		this.next = worker;
	}

}
