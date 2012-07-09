package com.sillycat.easytalker.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogsTopic1 {
	
	private static final String EXCHANGE_NAME = "topic_logs";
	
	private final static String SERVER_HOST = "www.neptune.com";

	public static void main(String[] argv) {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(SERVER_HOST);
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.exchangeDeclare(EXCHANGE_NAME, "topic");
			
			String queueName = channel.queueDeclare().getQueue();
			
			channel.queueBind(queueName, EXCHANGE_NAME, "lazy.#");
			
			System.out
					.println(" [*] Waiting for messages. To exit press CTRL+C");
			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(queueName, true, consumer);
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				String routingKey = delivery.getEnvelope().getRoutingKey();
				System.out.println(" [x] Received '" + routingKey + "':'"
						+ message + "'");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ignore) {
				}
			}
		}
	}
}
