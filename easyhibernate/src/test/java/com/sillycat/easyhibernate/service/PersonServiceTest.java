package com.sillycat.easyhibernate.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sillycat.easyapi.rest.json.Person;
import com.sillycat.easyhibernate.service.PersonService;

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
	}
	
	@Test
	public void getAll(){
		List<Person> list = personService.getAll();
		Assert.assertNotNull(list);
		Assert.assertEquals(true, list.size() > 0);
	}

}
