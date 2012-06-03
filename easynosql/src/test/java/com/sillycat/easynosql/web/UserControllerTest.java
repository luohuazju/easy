package com.sillycat.easynosql.web;

import junit.framework.Assert;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.sillycat.easynosql.core.ControllerTestBase;
import com.sillycat.easynosql.model.User;
import com.sillycat.easynosql.model.dto.UserListDTO;

public class UserControllerTest extends ControllerTestBase {

	public ObjectMapper jsonMapper;

	@Before
	public void setUp() {
		super.setUp();
		jsonMapper = new ObjectMapper();
	}

	@Test
	public void records() throws Exception {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		mockRequest.setRequestURI("/users/records");
		mockRequest.setMethod("GET");
		this.excuteAction(mockRequest, mockResponse);
		Assert.assertEquals(mockResponse.getStatus(), 200);
		UserListDTO result = jsonMapper.readValue(
				mockResponse.getContentAsString(), UserListDTO.class);
		Assert.assertNotNull(result.getUsers());
	}

	@Test
	public void get() throws Exception {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();

		mockRequest.setContentType(MediaType.APPLICATION_JSON_VALUE);
		mockRequest.setMethod("GET");
		mockRequest.setRequestURI("/users/get");

		User user = new User();
		user.setUsername("john");  
		String jsonUser = jsonMapper.writeValueAsString(user);
		mockRequest.setContent(jsonUser.getBytes());

		this.excuteAction(mockRequest, mockResponse);

		Assert.assertEquals(mockResponse.getStatus(), 200);
		User result = jsonMapper.readValue(mockResponse.getContentAsString(),
				User.class);
		Assert.assertNotNull(result.getUsername());
		Assert.assertEquals("john", result.getUsername());
	}

	@Test
	public void create() throws Exception {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();

		mockRequest.setContentType(MediaType.APPLICATION_JSON_VALUE);
		mockRequest.setMethod("POST");
		mockRequest.setRequestURI("/users/create");

		mockRequest.setParameter("username", "test1");
		mockRequest.setParameter("password", "111111");
		mockRequest.setParameter("firstName", "test1");
		mockRequest.setParameter("lastName", "test1");
		mockRequest.setParameter("role", "1");

		this.excuteAction(mockRequest, mockResponse);

		Assert.assertEquals(mockResponse.getStatus(), 200);
		User result = jsonMapper.readValue(mockResponse.getContentAsString(),
				User.class);
		Assert.assertNotNull(result.getUsername());
		Assert.assertEquals("test1", result.getUsername());
	}
}
