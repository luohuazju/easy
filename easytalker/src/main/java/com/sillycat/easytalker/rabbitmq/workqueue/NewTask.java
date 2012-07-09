package com.sillycat.easytalker.rabbitmq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {
	
	private static final String TASK_QUEUE_NAME = "task_queue";
	
	private final static String SERVER_HOST = "www.neptune.com";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(SERVER_HOST);
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		boolean durable = true;
		channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
		
		String message = "job";
		for(int i = 0;i<10;i++){
			message = "job" + i;
			channel.basicPublish("", TASK_QUEUE_NAME,
				MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}
		channel.close();
		connection.close();
	}

	
}
