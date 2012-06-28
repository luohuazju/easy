package com.sillycat.easywebflow.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionCookieUtil {

	public static final String COOKIE_SECURITY_TOKEN_NAME = "cookie_security_token";

	public static final String SESSION_SECURITY_TOKEN_NAME = "session_security_token";

	public static void writeValueToCookie(String key, String value,
			HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(-1);
		String contextPath = null;
		if (request != null) {
			contextPath = request.getContextPath();
		}
		if ((contextPath != null) && (contextPath.length() > 0)) {
			cookie.setPath(contextPath);
		} else {
			cookie.setPath("/");
		}
		if (response != null) {
			response.addCookie(cookie);
		}
	}

	public static String getValueFromCookie(String key,
			HttpServletRequest request) {

		String sessionId_fromCookie = "";
		Cookie[] cookies_array = null;
		if (request != null) {
			cookies_array = request.getCookies();
		}

		if (cookies_array != null && cookies_array.length > 0) {
			for (int i = 0; i < cookies_array.length; i++) {
				Cookie cookie = cookies_array[i];
				if (cookie.getName().equalsIgnoreCase(key)) {
					sessionId_fromCookie = cookie.getValue();
					break;
				}
			}
		}
		return sessionId_fromCookie;
	}

	public static void putValueinSession(String key, String value,
			HttpServletRequest request) {
		HttpSession session = null;
		if (request != null) {
			session = request.getSession();
		}
		if (session != null) {
			session.setAttribute(key, value);
		}
	}

	public static String fetchValuefromSession(String key,
			HttpServletRequest request) {
		String tokenValuefromSession = "";
		HttpSession session = null;
		if (request != null) {
			session = request.getSession();
		}
		if (session != null) {
			if (session.getAttribute(key) != null) {
				tokenValuefromSession = (String) session.getAttribute(key);
			}
		}
		return tokenValuefromSession;
	}

}
