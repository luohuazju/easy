package com.sillycat.easytalker.rabbitmq.hello;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TestProducer {

	private final static String QUEUE_NAME = "hello";
	
	private final static String SERVER_HOST = "localhost";

	public static void main(String[] args) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(SERVER_HOST);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = "Hello World!";
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");

		channel.close();
		connection.close();
	}

}
