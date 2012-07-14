package com.sillycat.easynio.plugins.mina.chain;

public interface IWorker {

	public void handle(Iphone iphone);

	public void setNextWorker(IWorker worker);
}
