package com.sillycat.easyrestserver.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.DispatcherServlet;

import com.sillycat.easyrestserver.core.MockWebApplication;
import com.sillycat.easyrestserver.core.MockWebApplicationContextLoader;
import com.sillycat.easyrestserver.service.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = MockWebApplicationContextLoader.class)
@MockWebApplication(name = "easyrestserver", locations = "classpath:test-context.xml")
public class PersonControllerTest {

	@Autowired
	private DispatcherServlet servlet;
	
	@Mock
	private PersonService mockPersonService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void get() throws ServletException, IOException {
		MockHttpServletRequest request = new MockHttpServletRequest("GET",
				"/person/1");
		MockHttpServletResponse response = new MockHttpServletResponse();

		servlet.service(request, response);
		String results = response.getContentAsString().trim();
		Assert.assertEquals(
				"{\"id\":1,\"personName\":\"UserName1\",\"company\":{\"id\":1,\"companyName\":\"CompanyName1\",\"persons\":null}}",
				results);
	}

}
