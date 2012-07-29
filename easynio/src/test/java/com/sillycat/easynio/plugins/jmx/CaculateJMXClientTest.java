package com.sillycat.easynio.plugins.jmx;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/test-context.xml" })
public class CaculateJMXClientTest {

	@Autowired
	@Qualifier("caculateJMXClient")
	private CaculateJMXInterface caculateJMXClient;

	@Test
	public void dumy() {
		Assert.assertTrue(true);
	}

	@Test
	public void add() {
		int sum = caculateJMXClient.add(100, 200);
		Assert.assertEquals(sum, 300);
	}
	
	@Test
	public void echo(){
		System.out.println(caculateJMXClient.echo("sillycat"));
	}

}
