package com.sillycat.easyrestserver.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
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
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import com.sillycat.easyrestserver.core.MockWebApplication;
import com.sillycat.easyrestserver.core.MockWebApplicationContextLoader;
import com.sillycat.easyrestserver.model.Company;
import com.sillycat.easyrestserver.model.Person;
import com.sillycat.easyrestserver.service.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = MockWebApplicationContextLoader.class)
@MockWebApplication(name = "easyrestserver", locations = "classpath:test-context.xml")
public class PersonControllerTest {

	@Autowired
	private DispatcherServlet servlet;

	@Mock
	private PersonService mockPersonService;

	ObjectMapper mapper;
	
	AnnotationMethodHandlerAdapter handlerAdapter;

	Person person;
	
	PersonController personController;

	@Before
	public void setUp() throws ServletException, IOException {
		MockitoAnnotations.initMocks(this);
		mapper = new ObjectMapper();
		person = new Person();
		person.setCompany(new Company());
		person.setId(1);
		person.setPersonName("person1");
		
		handlerAdapter = new AnnotationMethodHandlerAdapter();
        HttpMessageConverter<?>[] messageConverters = {new MappingJacksonHttpMessageConverter()};
        handlerAdapter.setMessageConverters(messageConverters);
        
        personController = new PersonController();
		personController.setPersonService(mockPersonService);
		
	}
	
	

	@Test
	public void get() throws Exception {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest("GET",
				"/person/1");
		mockRequest.setContentType(MediaType.APPLICATION_JSON_VALUE);
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();

		Mockito.when(mockPersonService.get(1)).thenReturn(person);
		
		servlet.service(mockRequest, mockResponse);

		handlerAdapter.handle(mockRequest, mockResponse, personController);
		Assert.assertEquals(mockResponse.getStatus(), 200);
		Person actualPerson = mapper.readValue(
				mockResponse.getContentAsString(), Person.class);
		Assert.assertEquals(actualPerson.getId(), person.getId());
	}
	
	@Test
	public void add() throws Exception{
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		mockRequest.setContentType(MediaType.APPLICATION_JSON_VALUE);
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		mockRequest.setMethod("POST");
	    mockRequest.setRequestURI("/person");
	    
	    String jsonPerson = mapper.writeValueAsString(person);
	    mockRequest.setContent(jsonPerson.getBytes());

	    servlet.service(mockRequest, mockResponse);
	    
	    handlerAdapter.handle(mockRequest, mockResponse, personController);

	    Assert.assertEquals(mockResponse.getStatus(), 200);
		Person actualPerson = mapper.readValue(
				mockResponse.getContentAsString(), Person.class);
		Assert.assertEquals(actualPerson.getId(), person.getId());
	}

}
