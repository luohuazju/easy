package com.sillycat.easywebflow.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sillycat.easywebflow.util.SessionCookieUtil;
import com.sillycat.easywebflow.util.SecurityTokenUtil;

@Controller
@RequestMapping("/user.do")
class UserController {
	
	@RequestMapping(params = "method=login" , method = RequestMethod.POST )
	public ModelAndView loginpost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String username = request.getParameter("j_username");
		String password = request.getParameter("j_password");
		if(username == null || username.equals("")){
			username = request.getSession().getAttribute("username");	
		}else{
			request.getSession().setAttribute("username", username);
		}
		
		ModelAndView view = new ModelAndView("redirect:/echo.do?method=success");
	    System.out.println("POST username = " + username + " password = " + password + " sessionId = " + request.getSession().getId());
		view.addObject("username", username);
		view.addObject("password", password);
		
		String token = SecurityTokenUtil.getRandomIntNumCharacter(30);
		SessionCookieUtil.writeValueToCookie(SessionCookieUtil.COOKIE_SECURITY_TOKEN_NAME,token,request,response);
		SessionCookieUtil.putValueinSession(SessionCookieUtil.SESSION_SECURITY_TOKEN_NAME, token, request);
		return view;
	}
			
	@RequestMapping(params = "method=login" , method = RequestMethod.GET )
	public ModelAndView loginget(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String username = request.getParameter("j_username");
		String password = request.getParameter("j_password");
		if(username == null || username.equals("")){
			username = request.getSession().getAttribute("username");
		}else{
			request.getSession().setAttribute("username", username);
		}
		try {
			Thread.sleep(2000);
		}catch(Exception e){
			System.out.println("GET username = " + username + " password = " + password + " sessionId = " + request.getSession().getId());
		}
		ModelAndView view = new ModelAndView("redirect:/echo.do?method=success");
		System.out.println("GET username = " + username + " password = " + password + " sessionId = " + request.getSession().getId());
		view.addObject("username", username);
		view.addObject("password", password);
		
		String token = SecurityTokenUtil.getRandomIntNumCharacter(30);
		SessionCookieUtil.writeValueToCookie(SessionCookieUtil.COOKIE_SECURITY_TOKEN_NAME,token,request,response);
		SessionCookieUtil.putValueinSession(SessionCookieUtil.SESSION_SECURITY_TOKEN_NAME, token, request);
		
		return view;
	}
			
	
}