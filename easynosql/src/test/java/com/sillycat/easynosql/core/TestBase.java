package com.sillycat.easynosql.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

import com.sillycat.easygroovyplugin.servlet.proxy.ProxyAwareAnnotationMethodHandlerAdapter;

public class TestBase {

	private HandlerMapping handlerMapping;
	private HandlerAdapter handlerAdapter;

	private XmlWebApplicationContext context;

	@Before
	public void setUp() {
		String[] configs = { "file:src/test/resources/test-context.xml" };
		context = new XmlWebApplicationContext();
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
						.getBeanNamesForType(ProxyAwareAnnotationMethodHandlerAdapter.class)[0]);
	}
	
	public XmlWebApplicationContext getContext(){
		return context;
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
