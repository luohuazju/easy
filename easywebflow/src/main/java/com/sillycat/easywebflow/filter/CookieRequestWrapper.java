package com.sillycat.easywebflow.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CookieRequestWrapper extends HttpServletRequestWrapper {

	private HttpServletResponse response = null;

	public CookieRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpSession getSession() {
		Thread currentThread = Thread.currentThread();  // 获得当前的线程          
		String threadName = currentThread.getName();  
		
		HttpSession session = super.getSession();
		
		System.out.println(threadName + " getSession CookieRequestWrapper class entered here!!!!!!!!!!!!!! and sessionId=" + session.getId());
		
		processSessionCookie(session);
		return session;
	}

	public HttpSession getSession(boolean create) {
		Thread currentThread = Thread.currentThread();  // 获得当前的线程          
		String threadName = currentThread.getName();  
		
		HttpSession session = super.getSession(create);
		System.out.println(threadName + " getSession boolean  CookieRequestWrapper class entered here!!!!!!!!!!!!!! and sessionId=" + session.getId());
		processSessionCookie(session);
		return session;
	}

	private void processSessionCookie(HttpSession session) {
		Thread currentThread = Thread.currentThread();  // 获得当前的线程          
		String threadName = currentThread.getName();  
		
		if (null == response || null == session) {
			return;
		}
		// cookieOverWritten
		Object cookieOverWritten = getAttribute("COOKIE_OVERWRITTEN_FLAG");
		if (null == cookieOverWritten && isSecure()
		//if (isSecure()
				&& isRequestedSessionIdFromCookie() && session.isNew()) {
			System.out.println(threadName + " CookieRequestWrapper class entered here!!!!!!!!!!!!!! and sessionId=" + session.getId());
			Cookie cookie = new Cookie("JSESSIONID", session.getId());
			cookie.setMaxAge(-1); 
			String contextPath = getContextPath();
			if ((contextPath != null) && (contextPath.length() > 0)) {
				cookie.setPath(contextPath);
			} else {
				cookie.setPath("/");
			}
			response.addCookie(cookie); // 
			setAttribute("COOKIE_OVERWRITTEN_FLAG", "true");
		}
	}
}
