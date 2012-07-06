package com.sillycat.easytalker.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogTopic {

	private static final String EXCHANGE_NAME = "topic_logs";

	private final static String SERVER_HOST = "rd.digby.com";

	public static void main(String[] argv) {
		Connection connection = null;
		Channel channel = null;

		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(SERVER_HOST);
			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.exchangeDeclare(EXCHANGE_NAME, "topic");

			channel.basicPublish(EXCHANGE_NAME, "bird.white.fly", null,
					"fly bird, haha.".getBytes());
			channel.basicPublish(EXCHANGE_NAME, "bird.black.walk", null,
					"walking cock!".getBytes());
			channel.basicPublish(EXCHANGE_NAME, "lazy.black.sleep", null,
					"sleep warm!".getBytes());
			channel.basicPublish(EXCHANGE_NAME, "fast.white.rabbit", null,
					"running fast rabbit!".getBytes());

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
