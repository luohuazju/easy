package com.sillycat.easyhibernate.controller;
/**
package com.sillycat.easyrestserver.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sillycat.easyapi.rest.json.Person;
import com.sillycat.easyrestserver.exception.JsonServiceException;
import com.sillycat.easyrestserver.service.PersonService;

@Controller
@RequestMapping("/*")
public class PersonController {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private PersonService personService;
	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
    	log.info("A REST server home page.");
        return "home";
    }

	@RequestMapping(method = RequestMethod.GET, value = "/person/{id}")
	public @ResponseBody
	Person get(@PathVariable("id") String id) throws JsonServiceException {
		log.info("Get method is invoked. ==========================");
		log.info("id=" + id);
		if (id.equalsIgnoreCase("13")) {
			throw new JsonServiceException("101",
					"Id can not be 13. Because I do not like it.");
		}
		Person person = personService.get(Integer.valueOf(id));
		log.info("========================================================");
		return person;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/person/persons")
	public @ResponseBody
	List<Person> getAll() {
		log.info("GetAll method is invoked.========================");
		List<Person> persons = personService.getAll();
		log.info("========================================================");
		return persons;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/person/person")
	public @ResponseBody
	Person add(@RequestBody Person p) {
		log.info("Add method is invoked.=============================");
		log.info("person=" + p);
		personService.save(p);
		log.info("==========================================================");
		return p;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/person/{id}")
	public @ResponseBody
	Person update(@RequestBody Person p, @PathVariable("id") String id) {
		log.info("Update method is invoked.===========================");
		log.info("id=" + id + " person=" + p);
		personService.save(p);
		log.info("===========================================================");
		return p;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/person/{id}")
	public @ResponseBody
	void remove(@PathVariable("id") String id) {
		log.info("Remove method is invoked.=============================");
		log.info("id=" + id);
		personService.delete(Integer.parseInt(id));
		log.info("==============================================================");
	}

	@ExceptionHandler(JsonServiceException.class)
	public void handleJsonServiceException(JsonServiceException exception,
			HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_NOT_FOUND,
				exception.getErrorMessage());
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

}
**/
