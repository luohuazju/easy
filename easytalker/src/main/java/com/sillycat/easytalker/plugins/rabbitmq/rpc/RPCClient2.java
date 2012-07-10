package com.sillycat.easytalker.plugins.rabbitmq.rpc;

import java.util.UUID;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class RPCClient2 {

	private Connection connection;
	private Channel channel;

	private String requestQueueName = "rpc_queue";

	private String replyQueueName;
	private QueueingConsumer consumer;

	private final static String SERVER_HOST = "www.neptune.com";

	public RPCClient2() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(SERVER_HOST);
		connection = factory.newConnection();
		channel = connection.createChannel();

		replyQueueName = channel.queueDeclare().getQueue();

		consumer = new QueueingConsumer(channel);
		channel.basicConsume(replyQueueName, true, consumer);
	}

	public String call(String message) throws Exception {
		String response = null;
		String corrId = UUID.randomUUID().toString();
		BasicProperties props = new BasicProperties.Builder()
				.correlationId(corrId).replyTo(replyQueueName).build();
		channel.basicPublish("", requestQueueName, props, message.getBytes());

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			if (delivery.getProperties().getCorrelationId().equals(corrId)) {
				// if the id is the same, we will get the response
				response = new String(delivery.getBody(), "UTF-8");
				break;
			}
		}
		return response;
	}

	public void close() throws Exception {
		connection.close();
	}

	public static void main(String[] argv) {
		RPCClient2 fibonacciRpc = null;
		String response = null;
		try {
			fibonacciRpc = new RPCClient2();
			System.out.println(" [x] Requesting fib(32)");
			response = fibonacciRpc.call("32");
			System.out.println(" [.] Got '" + response + "'");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fibonacciRpc != null) {
				try {
					fibonacciRpc.close();
				} catch (Exception ignore) {
				}
			}
		}
	}
}
