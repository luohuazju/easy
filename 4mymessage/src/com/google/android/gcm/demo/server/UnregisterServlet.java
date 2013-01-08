package com.google.android.gcm.demo.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnregisterServlet extends BaseServlet {

	private static final long serialVersionUID = -3134012774851828839L;
	
	private static final String PARAMETER_REG_ID = "regId";

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		String regId = getParameter(req, PARAMETER_REG_ID);
		Datastore.unregister(regId);
		setSuccess(resp);
	}

}
