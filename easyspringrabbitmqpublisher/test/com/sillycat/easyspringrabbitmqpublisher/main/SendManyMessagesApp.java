package com.sillycat.easyspringrabbitmqpublisher.main;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SendManyMessagesApp {

	public static void main(String[] args) {
		ApplicationContext context = new GenericXmlApplicationContext(
				"classpath:main-context.xml");
		AmqpTemplate template = context.getBean(AmqpTemplate.class);

		// Sync send 100 Messages
		for (int i = 0; i < 100; i++) {
			template.convertAndSend("myqueue2", "message number " + i);
			System.out.println("Done " + i);
		}
		
	}

}
