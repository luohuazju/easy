package com.sillycat.easyrestserver.controller;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

public class PersonControllerTest {
	private static HandlerMapping handlerMapping;
	private static HandlerAdapter handlerAdapter;

	private static MockServletContext mockServletContent;

	@BeforeClass
	public static void setUp() {
		String[] configs = { "file:src/test/resources/test-context.xml" };
		XmlWebApplicationContext context = new XmlWebApplicationContext();
		context.setConfigLocations(configs);
		mockServletContent = new MockServletContext();
		context.setServletContext(mockServletContent);
		context.refresh();
		mockServletContent.setAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				context);
		handlerMapping = (HandlerMapping) context
				.getBean(DefaultAnnotationHandlerMapping.class);
		handlerAdapter = (HandlerAdapter) context.getBean(context
				.getBeanNamesForType(AnnotationMethodHandlerAdapter.class)[0]);
	}

	@Test
	public void get() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		request.setRequestURI("/person/1");
		request.setMethod("GET");

		// HandlerMapping
		HandlerExecutionChain chain = handlerMapping.getHandler(request);
		Assert.assertEquals(true,
				chain.getHandler() instanceof PersonController);

		// HandlerAdapter
		final ModelAndView modelAndView = handlerAdapter.handle(request,
				response, chain.getHandler());

		Assert.assertNotNull(modelAndView);
	}
}
