package com.sillycat.easyrestserver.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sillycat.easyrestserver.model.Person;
import com.sillycat.easyrestserver.service.PersonService;

@Controller
@RequestMapping("/person")
public class PersonController {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private PersonService personService;

	@RequestMapping(method = RequestMethod.GET, value = "/person/{id}")
	public @ResponseBody
	Person get(@PathVariable("id") String id) {
		log.info("Get method is invoked. ==========================");
		log.info("id=" + id);
		Person person = personService.get(Integer.valueOf(id));
		log.info("========================================================");
		return person;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/persons")
	public @ResponseBody
	List<Person> getAll() {
		log.info("GetAll method is invoked.========================");
		List<Person> persons = personService.getAll();
		log.info("========================================================");
		return persons;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/person")
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

}
