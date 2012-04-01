package com.sillycat.easyrestserver.base;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseTestCase extends TestCase {

	protected ApplicationContext ctx = null;

	@Override
	protected void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath:test-context.xml");
		super.setUp();
	}
	
	

}
