package com.sillycat.easytalker.kafka;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.javaapi.producer.ProducerData;
import kafka.producer.ProducerConfig;

public class TestProducerMain {

	public static void main(String[] args) {
		Properties props2 = new Properties();
		props2.put("zk.connect", "122.70.144.208:2181");
		//props2.put("zk.connect", "192.168.56.101:2181");
		props2.put("serializer.class", "kafka.serializer.StringEncoder");
		// This is added by myself for changing the default timeout 6000.
		props2.put("zk.connectiontimeout.ms", "15000");
		ProducerConfig config = new ProducerConfig(props2);
		Producer<String, String> producer = new Producer<String, String>(config);

		// The message is sent to a randomly selected partition registered in ZK
		ProducerData<String, String> data = new ProducerData<String, String>(
				"test", "test-message,it is ok now.adsfasdf1111222");
		producer.send(data);
		producer.close();
	}
}
