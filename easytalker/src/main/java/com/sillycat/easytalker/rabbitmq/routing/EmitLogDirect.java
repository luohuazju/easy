package com.sillycat.easytalker.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogDirect {
	
	private static final String EXCHANGE_NAME = "direct_logs";
	
	private final static String SERVER_HOST = "www.neptune.com";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(SERVER_HOST);
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		
		channel.basicPublish(EXCHANGE_NAME, "info", null, "hello info".getBytes());
		channel.basicPublish(EXCHANGE_NAME, "error", null, "hello error".getBytes());
		channel.basicPublish(EXCHANGE_NAME, "warning", null, "hello warning".getBytes());
		channel.basicPublish(EXCHANGE_NAME, "debug", null, "hello debug".getBytes());
		
		channel.close();
		connection.close();
	}

}
