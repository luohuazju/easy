package com.sillycat.easyhibernate.service;

import java.util.List;

import com.sillycat.easyapi.rest.json.Person;

public interface PersonService {
	
	public Person get(Integer id);

	public void delete(Integer id);

	public void save(Person person);

	public List<Person> getAll();

}
