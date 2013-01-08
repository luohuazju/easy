package com.sillycat.easymymessage;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GuestbookServlet extends HttpServlet {

	private static final long serialVersionUID = -8974156249244283590L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user != null) {
			resp.setContentType("text/plain");
			resp.getWriter().println(
					"Hello, " + user.getNickname() + ". Your email will be "
							+ user.getEmail());
		} else {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
		}
	}
}
