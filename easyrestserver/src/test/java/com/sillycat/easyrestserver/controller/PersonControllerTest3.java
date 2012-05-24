package com.sillycat.easyrestserver.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;

import com.sillycat.easyrestserver.model.Company;
import com.sillycat.easyrestserver.model.Person;
import com.sillycat.easyrestserver.service.PersonService;

/**
 * use mockito to mock our service/manager
 * new Controller and use handler to invoke the controller
 * @author karl
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
public class PersonControllerTest3 {

	@Mock
	private PersonService mockPersonService;
	
	@Autowired
	HandlerAdapter handlerAdapter;

	ObjectMapper jsonMapper;
	Person person;
	PersonController personController;
	MockHttpServletRequest mockRequest;
	MockHttpServletResponse mockResponse;

	@Before
	public void setUp() throws ServletException, IOException {
		MockitoAnnotations.initMocks(this);
		jsonMapper = new ObjectMapper();
		person = new Person();
		person.setCompany(new Company());
		person.setId(3);
		person.setPersonName("person3");

		personController = new PersonController();
		personController.setPersonService(mockPersonService);

		mockRequest = new MockHttpServletRequest();
		mockRequest.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING,
				true);

		mockResponse = new MockHttpServletResponse();
	}

	@Test
	public void get() throws Exception {
		mockRequest.setMethod("GET");
		mockRequest.setRequestURI("/person/3");
		mockRequest.setContentType(MediaType.APPLICATION_JSON_VALUE);
		mockResponse = new MockHttpServletResponse();

		Mockito.when(mockPersonService.get(3)).thenReturn(person);

		handlerAdapter.handle(mockRequest, mockResponse, personController);
		Assert.assertEquals(mockResponse.getStatus(), 200);
		Person actualPerson = jsonMapper.readValue(
				mockResponse.getContentAsString(), Person.class);
		Assert.assertEquals(actualPerson.getId(), person.getId());
	}

	//@Test
	public void add() throws Exception {
		mockRequest.setContentType(MediaType.APPLICATION_JSON_VALUE);
		mockRequest.setMethod("POST");
		mockRequest.setRequestURI("/person");

		person.setId(null);
		String jsonPerson = jsonMapper.writeValueAsString(person);
		mockRequest.setContent(jsonPerson.getBytes());
		

		handlerAdapter.handle(mockRequest, mockResponse, personController);

		Assert.assertEquals(mockResponse.getStatus(), 200);
	}

}
