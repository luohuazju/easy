package com.google.android.gcm.demo.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeServlet extends BaseServlet {

	private static final long serialVersionUID = -7175585141480592533L;
	
	static final String ATTRIBUTE_STATUS = "status";

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		out.print("<html><body>");
		out.print("<head>");
		out.print("  <title>GCM Demo</title>");
		out.print("  <link rel='icon' href='favicon.png'/>");
		out.print("</head>");
		String status = (String) req.getAttribute(ATTRIBUTE_STATUS);
		if (status != null) {
			out.print(status);
		}
		int total = Datastore.getTotalDevices();
		if (total == 0) {
			out.print("<h2>No devices registered!</h2>");
		} else {
			out.print("<h2>" + total + " device(s) registered!</h2>");
			out.print("<form name='form' method='POST' action='sendAll'>");
			out.print("<input type='submit' value='Send Message' />");
			out.print("</form>");
		}
		out.print("</body></html>");
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

}
