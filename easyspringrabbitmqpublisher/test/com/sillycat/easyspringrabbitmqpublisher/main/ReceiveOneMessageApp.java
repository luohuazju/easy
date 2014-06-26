package com.sillycat.easyspringrabbitmqpublisher.main;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class ReceiveOneMessageApp {

	public static void main(String[] args) {
		ApplicationContext context = new GenericXmlApplicationContext(
				"classpath:main-context.xml");
		AmqpTemplate template = context.getBean(AmqpTemplate.class);

		// Sync receive Message
		String foo = (String) template.receiveAndConvert("myqueue1");
		System.out.println("What I get from Sync = " + foo);

	}

}
