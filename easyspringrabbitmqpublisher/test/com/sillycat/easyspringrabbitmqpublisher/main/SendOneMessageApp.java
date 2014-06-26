package com.sillycat.easyspringrabbitmqpublisher.main;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SendOneMessageApp {

	public static void main(String[] args) {
		ApplicationContext context = new GenericXmlApplicationContext(
				"classpath:main-context.xml");
		AmqpTemplate template = context.getBean(AmqpTemplate.class);

		// Sync send Message
		template.convertAndSend("myqueue1", "message1");
		System.out.println("Done");
	}

}
