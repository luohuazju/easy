package com.sillycat.easywebflow.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SessionFixationProtectionFilter implements Filter {

	private final static Log log = LogFactory
			.getLog(SessionFixationProtectionFilter.class);

	private int num = 1;

	private boolean migrateSessionAttributes = true;

	private Map<String, Map<String, Object>> sessionMap = new HashMap<String, Map<String, Object>>();

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse serlvetResponse, FilterChain chain)
			throws IOException, ServletException {

		Thread currentThread = Thread.currentThread();
		String threadName = currentThread.getName();

		if (!(servletRequest instanceof HttpServletRequest)) {
			log.error("Can only process HttpServletRequest");
			throw new ServletException("Can only process HttpServletRequest");
		}

		if (!(serlvetResponse instanceof HttpServletResponse)) {
			log.error("Can only process HttpServletResponse");
			throw new ServletException("Can only process HttpServletResponse");
		}

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) serlvetResponse;

		// read cookie
		Cookie[] cookies_array = request.getCookies();
		String sessionId_fromCookie = "";
		if (cookies_array != null && cookies_array.length > 0) {
			for (int i = 0; i < cookies_array.length; i++) {
				Cookie cookie = cookies_array[i];
				if (cookie.getName().equalsIgnoreCase("JSESSIONID")) {
					sessionId_fromCookie = cookie.getValue();
					break;
				}
			}
		}

		log.debug(threadName + " filter count = " + num++
				+ " sessionId_fromCookie=" + sessionId_fromCookie);

		// map to hold all the parameters
		HashMap<String, Object> attributesToMigrate = null;

		// get session
		HttpSession session = request.getSession(false);

		if (session == null && request.isRequestedSessionIdValid() == false) {
			log.debug(threadName
					+ " how did this happen, there is no session!!!!!!!!!!!! + sessionId_fromCookie="
					+ sessionId_fromCookie);
			// if no session, there is nothing to deal
			// chain.doFilter(request, response);
			// return;
		}

		String originalSessionId = "";

		if (session != null && request.isRequestedSessionIdValid() != false) {
			originalSessionId = session.getId();
			// save the attributes in map
			if (migrateSessionAttributes) {
				attributesToMigrate = new HashMap<String, Object>();
				Enumeration<?> enumer = session.getAttributeNames();
				while (enumer.hasMoreElements()) {
					try {
						// Thread.sleep(2000);
						String key = (String) enumer.nextElement();
						if (session != null
								&& request.isRequestedSessionIdValid() != false) {
							attributesToMigrate.put(key,
									session.getAttribute(key));
						}
						// } catch (InterruptedException e) {
						// log.error( threadName + "error message: " + e +
						// " sessionId=" + originalSessionId);
					} catch (Exception e) {
						log.error(threadName + " error message " + e
								+ " sessionId=" + originalSessionId);
					}
				}
				sessionMap.put(originalSessionId, attributesToMigrate);
			}
		} else {
			originalSessionId = sessionId_fromCookie;
		}

		// kill the old session
		if (session != null && request.isRequestedSessionIdValid() != false) {
			if (log.isDebugEnabled()) {
				log.debug(threadName + " Invalidating session with Id "
						+ originalSessionId + " start!");
			}
			session.invalidate();
			if (log.isDebugEnabled()) {
				log.debug(threadName + "Invalidating session with Id "
						+ originalSessionId + " end!");
			}
			// session.setMaxInactiveInterval(10);
		}

		session = request.getSession(true); // we now have a new session
		if (log.isDebugEnabled()) {
			log.debug(threadName + "Started new session: " + session.getId());
		}

		if (sessionMap.containsKey(originalSessionId)) {
			log.debug(threadName + "getting session value from map: "
					+ originalSessionId);
			attributesToMigrate = (HashMap<String, Object>) sessionMap
					.get(originalSessionId);
		}
		// migrate the attribute to new session
		if (attributesToMigrate != null) {
			Iterator<?> iter = attributesToMigrate.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<?, ?> entry = (Entry<?, ?>) iter.next();
				try {
					session.setAttribute((String) entry.getKey(),
							entry.getValue());
					// Thread.sleep(2000);
					// } catch (InterruptedException e) {
					// log.error(threadName + " error message " + e +
					// " new SessionId=" + session.getId());
				} catch (Exception e) {
					log.error(threadName + " error message " + e
							+ " new SessionId=" + session.getId());
				}
			}
		}

		CookieRequestWrapper wrapperRequest = new CookieRequestWrapper(request);
		wrapperRequest.setResponse(response);
		chain.doFilter(wrapperRequest, response);
	}

	public void destroy() {

	}

	public boolean isMigrateSessionAttributes() {
		return migrateSessionAttributes;
	}

	public void setMigrateSessionAttributes(boolean migrateSessionAttributes) {
		this.migrateSessionAttributes = migrateSessionAttributes;
	}

}
