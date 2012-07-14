package com.sillycat.easynio.plugins.mina.chain;

public class IphoneFactory {

	public static void main(String[] args) {

		IWorker worker1 = new WorkerDevice();   
		IWorker worker2 = new WorkerSoftware();   
		IWorker worker3 = new WorkerIntegration();   
		  
		worker1.setNextWorker(worker2);   
		worker2.setNextWorker(worker3);   
		
		Iphone iphone = new Iphone();   
		worker1.handle(iphone);   
		System.out.println(iphone.getState());  
	}

}
