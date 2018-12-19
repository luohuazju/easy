package com.sillycat.easytalker.plugins.rabbitmq.hello;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TestProducer {

	// private final static String QUEUE_NAME = "hello";
	private final static String QUEUE_NAME = "contact_manager";

	private final static String SERVER_HOST = "localhost";

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(SERVER_HOST);
		factory.setUsername("carl");
		factory.setPassword("test");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		for (int i = 0; i < 30000; i++) {
			Map<String, Object> others = new HashMap<String, Object>();
			others.put("x-dead-letter-exchange", "contact_manager_undefined.dead_letter");
			channel.queueDeclare(QUEUE_NAME, true, false, false, others);
			String message = "{\r\n" + "  \"action\": \"UPDATED\",\r\n" + "  \"type\": \"REGSTATUS\",\r\n"
					+ "  \"serialNumber\": \"KQ6B2409FAC68\",\r\n"
					+ "  \"pairingToken\": \"2bfb8403-1f14-4d22-a5e8-00b36c3341f2\",\r\n"
					+ "  \"extension\": \"6741302\",\r\n" + "  \"platform\": \"ICON_600\",\r\n"
					+ "  \"currentFirmwareVersion\": \"3.7.0.2421\",\r\n"
					+ "  \"remoteProductID\": \"LifeSize Icon 600/LS_RM3_3.7.0 (2421)\",\r\n"
					+ "  \"localIP\": \"10.9.57.130\",\r\n" + "  \"token\": \"937e5a86ea45fc91\",\r\n"
					+ "  \"phoneModelNumber\": \"\",\r\n" + "  \"phoneSerialNumber\": \"\",\r\n"
					+ "  \"registrationStatus\": \"UNREGISTERED\",\r\n" + "  \"timestamp\": 1545166054967\r\n" + "}";
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}
		channel.close();
		connection.close();
	}

}
