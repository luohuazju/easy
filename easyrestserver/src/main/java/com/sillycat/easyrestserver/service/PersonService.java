package com.sillycat.easyrestserver.service;

import java.util.List;

import com.sillycat.easyrestserver.model.Person;

public interface PersonService {
	
	public Person get(Integer id);

	public void delete(Integer id);

	public void save(Person person);

	public List<Person> getAll();

}
