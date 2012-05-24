package com.sillycat.easywebflow.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FixationSessionServlet extends HttpServlet {

	private static final long serialVersionUID = 6511486287462282927L;

	protected void doGet(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {
		HttpSession curSession = httpServletRequest.getSession();
		String prevSession = curSession.getId();
		curSession.invalidate();
		// Should invalidate session
		curSession = httpServletRequest.getSession(true);
		// Should return new valid session, but
		// returns old session instead
		String newSession = curSession.getId();
		boolean testCondition = prevSession.equals(newSession);
		System.out.println("================================");
		System.out.println("session test = " + testCondition);
		System.out.println("old session = " + prevSession);
		System.out.println("new session = " + newSession);
	}

}
