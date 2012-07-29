package com.sillycat.easynio.plugins.jmx;

import java.io.IOException;

import mx4j.tools.adaptor.http.HttpAdaptor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MX4JClientMain {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"main-context.xml");
		HttpAdaptor httpAdaptor = (HttpAdaptor) ctx.getBean("httpAdaptor");
		try {
			httpAdaptor.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
