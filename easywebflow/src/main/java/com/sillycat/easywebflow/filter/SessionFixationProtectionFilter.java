package com.sillycat.easywebflow.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sillycat.easywebflow.util.SessionUtil;

public class SessionFixationProtectionFilter implements Filter {

	private boolean migrateSessionAttributes = true;

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse serlvetResponse, FilterChain chain)
			throws IOException, ServletException {
		if (!(servletRequest instanceof HttpServletRequest)) {
			throw new ServletException("Can only process HttpServletRequest");
		}

		if (!(serlvetResponse instanceof HttpServletResponse)) {
			throw new ServletException("Can only process HttpServletResponse");
		}

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) serlvetResponse;

		startNewSessionIfRequired(request, response);

		chain.doFilter(request, response);
	}

	public void destroy() {

	}

	protected void startNewSessionIfRequired(HttpServletRequest request,
			HttpServletResponse response) {
		SessionUtil
				.startNewSessionIfRequired(request, migrateSessionAttributes);
	}

	public boolean isMigrateSessionAttributes() {
		return migrateSessionAttributes;
	}

	public void setMigrateSessionAttributes(boolean migrateSessionAttributes) {
		this.migrateSessionAttributes = migrateSessionAttributes;
	}

}
