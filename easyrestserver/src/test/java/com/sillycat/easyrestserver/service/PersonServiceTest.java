package com.sillycat.easyrestserver.service;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sillycat.easyrestserver.model.Person;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"file:src/test/resources/test-context.xml" }) 
public class PersonServiceTest {

	@Autowired  
    @Qualifier("personService")  
    private PersonService personService;  

	@Test
	public void dumy() {
		Assert.assertTrue(true);
	}

	@Test
	public void get() {
		Person p = personService.get(Integer.valueOf(1));
		Assert.assertNotNull(p);
		System.out.println(p);
	}

}
