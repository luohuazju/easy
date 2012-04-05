package com.sillycat.easyrestserver.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerMapping;

import com.sillycat.easyrestserver.core.MockWebApplication;
import com.sillycat.easyrestserver.core.MockWebApplicationContextLoader;
import com.sillycat.easyrestserver.model.Company;
import com.sillycat.easyrestserver.model.Person;

/**
 * mock the servlet and use servlet to test our controller
 * servlet.service(mockRequest, mockResponse);
 * can not mock the service or manager in controller
 * @author karl
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = MockWebApplicationContextLoader.class)
@MockWebApplication(name = "easyrestserver", locations = "classpath:test-context.xml")
public class PersonControllerTest1 {

	@Autowired
	private DispatcherServlet servlet;

	ObjectMapper mapper;
	Person person;
	MockHttpServletRequest mockRequest;
	MockHttpServletResponse mockResponse;

	@Before
	public void setUp() throws ServletException, IOException {
		MockitoAnnotations.initMocks(this);
		mapper = new ObjectMapper();
		person = new Person();
		person.setCompany(new Company());
		person.setId(1);
		person.setPersonName("person1");
		
		mockRequest = new MockHttpServletRequest();
		mockRequest.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING,true);
		
		mockResponse = new MockHttpServletResponse();
	}
	

	@Test
	public void get() throws Exception {
		mockRequest.setMethod("GET");
		mockRequest.setRequestURI("/person/1");
		mockRequest.setContentType(MediaType.APPLICATION_JSON_VALUE);
		mockResponse = new MockHttpServletResponse();

		servlet.service(mockRequest, mockResponse);

		Person actualPerson = mapper.readValue(
				mockResponse.getContentAsString(), Person.class);
		Assert.assertEquals(actualPerson.getId(), person.getId());
	}
	
	@Test
	public void add() throws Exception{
		mockRequest.setContentType(MediaType.APPLICATION_JSON_VALUE);
		mockRequest.setMethod("POST");
	    mockRequest.setRequestURI("/person");
	    
	    String jsonPerson = mapper.writeValueAsString(person);
	    mockRequest.setContent(jsonPerson.getBytes());

	    servlet.service(mockRequest, mockResponse);
	    
	    Assert.assertEquals(mockResponse.getStatus(), 200);
		Person actualPerson = mapper.readValue(
				mockResponse.getContentAsString(), Person.class);
		Assert.assertEquals(actualPerson.getId(), person.getId());
	}


}
