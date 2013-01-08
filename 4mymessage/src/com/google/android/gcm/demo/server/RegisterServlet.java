package com.google.android.gcm.demo.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends BaseServlet {

	private static final long serialVersionUID = -2920356093260262071L;
	
	private static final String PARAMETER_REG_ID = "regId";

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		String regId = getParameter(req, PARAMETER_REG_ID);
		Datastore.register(regId);
		setSuccess(resp);
	}

}
