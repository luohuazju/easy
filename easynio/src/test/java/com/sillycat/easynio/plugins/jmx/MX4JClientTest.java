package com.sillycat.easynio.plugins.jmx;

import java.io.IOException;

import mx4j.tools.adaptor.http.HttpAdaptor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/main-context.xml" })
public class MX4JClientTest {

	@Autowired
	@Qualifier("httpAdaptor")
	private HttpAdaptor httpAdaptor;

	@Test
	public void start() {
		try {
			httpAdaptor.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
