package com.sillycat.easywebflow.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/echo.do")
class EchoController {

	@RequestMapping(params = "method=echo" ,method = RequestMethod.GET)
    public ModelAndView echo() throws IOException {
		System.out.println("echo in controller")
		return new ModelAndView("echo","user","sillycat")
    }
			
}