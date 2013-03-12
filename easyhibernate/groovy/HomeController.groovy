package com.sillycat.easyhibernate.web;

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import com.sillycat.easyhibernate.service.PersonService

@Controller
class UserController {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private PersonService personService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		log.info("A REST server home page.");
		return "home";
	}
			
}