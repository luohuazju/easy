package com.sillycat.easynio.plugins.jmx;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import mx4j.tools.adaptor.http.HttpAdaptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class JMXContextLoadListener extends ContextLoaderListener implements
		ServletContextListener {

	private final Log log = LogFactory.getLog(getClass());

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		ServletContext context = event.getServletContext();
		setupContext(context);
	}

	protected void setupContext(final ServletContext context) {
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
		HttpAdaptor httpAdaptor = (HttpAdaptor) ctx.getBean("httpAdaptor");
		try {
			httpAdaptor.start();
		} catch (IOException e) {
			log.error(e);
		}
	}

}
