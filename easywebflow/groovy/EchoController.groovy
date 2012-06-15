package com.sillycat.easywebflow.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/echo.do")
class EchoController {

	@RequestMapping(params = "method=echo" ,method = RequestMethod.GET)
    public ModelAndView echo() throws IOException {
		System.out.println("echo in controller with sessionId = " + request.getSession().getId());
		return new ModelAndView("echo","user","sillycat");
    }
	
	@RequestMapping(params = "method=displaylogin", method = RequestMethod.GET)
	public ModelAndView displayLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		System.out.println("displaylogin in controller with sessionId = " + request.getSession().getId());
		response.setHeader("Pragma", "no-cache")
		response.addHeader("Cache-Control", "no-cache")
		response.addHeader("Cache-Control", "no-store" )
		response.addHeader("Cache-Control", "must-revalidate" )
		response.setDateHeader("Expires", 0)
		response.flushBuffer()
	    return new ModelAndView("displaylogin");
	}
			
			
	@RequestMapping(params = "method=preparesession", method = RequestMethod.GET)
	public ModelAndView preparesession(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		System.out.println("preparesession in controller with sessionId = " + request.getSession().getId());
		request.getSession().setAttribute("title", "session and HTTPS/HTTP testing");
		return new ModelAndView("next");
	}
}