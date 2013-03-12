package com.sillycat.easyhibernate.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.BeforeClass;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

public class ControllerTestBase {

	private static HandlerMapping handlerMapping;
	private static HandlerAdapter handlerAdapter;

	@BeforeClass
	public static void setUp() {
		if (handlerMapping == null) {
			String[] configs = { "file:src/test/resources/test-context.xml" };
			XmlWebApplicationContext context = new XmlWebApplicationContext();
			context.setConfigLocations(configs);
			MockServletContext msc = new MockServletContext();
			context.setServletContext(msc);
			context.refresh();
			msc.setAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
					context);
			handlerMapping = (HandlerMapping) context
					.getBean(DefaultAnnotationHandlerMapping.class);
			handlerAdapter = (HandlerAdapter) context
					.getBean(context
							.getBeanNamesForType(AnnotationMethodHandlerAdapter.class)[0]);
		}
	}

	public ModelAndView excuteAction(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HandlerExecutionChain chain = handlerMapping.getHandler(request);

		request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true);

		final ModelAndView model = handlerAdapter.handle(request, response,
				chain.getHandler());
		return model;
	}

}
