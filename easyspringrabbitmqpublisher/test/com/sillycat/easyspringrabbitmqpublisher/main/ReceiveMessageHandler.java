package com.sillycat.easyspringrabbitmqpublisher.main;

import java.util.Date;

public class ReceiveMessageHandler {
	
	public void handleMessage(String message){
		System.out.println("Received " + message + " at " + new Date());
	}

}
