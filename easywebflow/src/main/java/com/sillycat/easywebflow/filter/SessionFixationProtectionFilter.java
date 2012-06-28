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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sillycat.easywebflow.util.SessionCookieUtil;

public class SessionFixationProtectionFilter implements Filter {

	private final Log log = LogFactory
			.getLog(SessionFixationProtectionFilter.class);

	public void doFilter(ServletRequest servletRequest,
			ServletResponse serlvetResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) serlvetResponse;

		String tokenValuefromCookie = SessionCookieUtil.getValueFromCookie(
				SessionCookieUtil.COOKIE_SECURITY_TOKEN_NAME, request);
		String tokenValuefromSession = SessionCookieUtil.fetchValuefromSession(
				SessionCookieUtil.SESSION_SECURITY_TOKEN_NAME, request);

		log.debug("COOKIE tokenValue = " + tokenValuefromCookie);
		log.debug("SESSION tokenValue = " + tokenValuefromSession);

		if (tokenValuefromSession != null
				&& !"".equals(tokenValuefromSession)
				&& !tokenValuefromSession
						.equalsIgnoreCase(tokenValuefromCookie)) {
			// there is token in session, and it is not equals from cookie
			request.getSession().invalidate();
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {

	}

}
