package com.sillycat.easyrestserver.service;

import com.sillycat.easyrestserver.base.BaseTestCase;
import com.sillycat.easyrestserver.model.Person;

public class PersonServiceTest extends BaseTestCase {

	private PersonService personService;

	public void setUp() throws Exception {
		super.setUp();
		personService = (PersonService) ctx.getBean("personService");
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testDumy() {
		assertTrue(true);
	}

	public void testGet() {
		Person p = personService.get(Integer.valueOf(1));
		assertNotNull(p);
		System.out.println(p);
	}

}
