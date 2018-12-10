package com.sillycat.easytalker.plugins.rabbitmq.rpc;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RPCClient1 {
	
	private Connection connection;
	  private Channel channel;
	  private String requestQueueName = "rpc_queue";
	  private String replyQueueName;
	
	private final static String SERVER_HOST = "centos-dev1";

	public RPCClient1() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(SERVER_HOST);
	    factory.setUsername("carl");
	    factory.setPassword("kaishi");
	    connection = factory.newConnection();
	    channel = connection.createChannel();
	    replyQueueName = channel.queueDeclare().getQueue();
	}

	public String call(String message) throws Exception {
		final String corrId = UUID.randomUUID().toString();
	    AMQP.BasicProperties props = new AMQP.BasicProperties
	            .Builder()
	            .correlationId(corrId)
	            .replyTo(replyQueueName)
	            .build();
	    channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));
	    final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);
	    channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
	      @Override
	      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
	        if (properties.getCorrelationId().equals(corrId)) {
	          response.offer(new String(body, "UTF-8"));
	        }
	      }
	    });
	    return response.take();
	}

	public void close() throws Exception {
		connection.close();
	}

	public static void main(String[] argv) {
		RPCClient1 fibonacciRpc = null;
		String response = null;
		try {
			fibonacciRpc = new RPCClient1();
			System.out.println(" [x] Requesting fib(31)");
			response = fibonacciRpc.call("31");
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
