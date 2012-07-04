package com.sillycat.easyopenidgoogle.manager;

import java.util.List;

import com.sillycat.easyopenidgoogle.model.Person;

public interface PersonManager {
	
	public Person get(Integer id);

	public void delete(Integer id);

	public void save(Person person);

	public List<Person> getAll();

}
