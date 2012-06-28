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
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SessionFixationProtectionFilterValidateIP implements Filter {

	private final static Log log = LogFactory
			.getLog(SessionFixationProtectionFilterValidateIP.class);

	private static final String SESSION_IP_FILTER_CONSTANT = "session_ip_filter_constant";

	private static final String SESSION_USER_AGENT_FILTER_CONSTANT = "session_user_agent_filter_constant";

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse serlvetResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) serlvetResponse;

		String current_clientip = "127.0.0.1";
		String current_clientagent = "useragent";

		String session_clientip = "";
		String session_clientagent = "";

		if (request.getRemoteAddr() != null
				&& !"".equals(request.getRemoteAddr())) {
			current_clientip = request.getRemoteAddr();
		}
		if (request.getHeader("User-Agent") != null
				&& !"".equals(request.getHeader("User-Agent"))) {
			current_clientagent = request.getHeader("User-Agent");
		}

		HttpSession session = request.getSession(false);
		if (session == null && request.isRequestedSessionIdValid() == false) {
			// session is empty, nothing need to do
			log.debug(" There is no session here !");
			chain.doFilter(request, response);
			return;
		}
		if (session.getAttribute(SESSION_IP_FILTER_CONSTANT) != null) {
			session_clientip = (String) session
					.getAttribute(SESSION_IP_FILTER_CONSTANT);
		}
		if (session.getAttribute(SESSION_USER_AGENT_FILTER_CONSTANT) != null) {
			session_clientagent = (String) session
					.getAttribute(SESSION_USER_AGENT_FILTER_CONSTANT);
		}

		log.debug(" current ip = " + current_clientip + " session ip = "
				+ session_clientip);
		log.debug(" current useragent = " + current_clientagent
				+ " session useragent = " + session_clientagent);

		if (session_clientip != null && !session_clientip.equals("")) {
			// session value is not null, so this is not the first request
			if (!session_clientip.equalsIgnoreCase(current_clientip)
					|| !session_clientagent
							.equalsIgnoreCase(current_clientagent)) {
				// the current user is not the previous one, kill the current
				// session
				String original_session_id = session.getId();
				log.debug(" invalidate the old sessionid = "
						+ original_session_id);
				session.invalidate();
				// generate new session
				session = request.getSession(true);
				log.debug(" newly create sessionid = " + session.getId());
			}
		}

		session.setAttribute(SESSION_IP_FILTER_CONSTANT, current_clientip);
		session.setAttribute(SESSION_USER_AGENT_FILTER_CONSTANT,
				current_clientagent);
		
		chain.doFilter(request, response);
	}

	public void destroy() {

	}

}
