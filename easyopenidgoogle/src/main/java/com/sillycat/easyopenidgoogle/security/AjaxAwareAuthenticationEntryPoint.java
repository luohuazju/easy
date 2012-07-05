package com.sillycat.easyopenidgoogle.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class AjaxAwareAuthenticationEntryPoint extends
		LoginUrlAuthenticationEntryPoint {

	private final Log log = LogFactory.getLog(this.getClass());

	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		if (request.getHeader("X-AjaxRequest") != null
				&& request.getHeader("X-AjaxRequest").equals("1")) {
			((HttpServletResponse) response).sendError(403, "");
			log.debug("Ajax parameter: " + request.getHeader("X-AjaxRequest"));
		} else {
			super.commence(request, response, authException);
			log.debug("Ajax parameter: " + request.getHeader("X-AjaxRequest"));
		}
	}

}
