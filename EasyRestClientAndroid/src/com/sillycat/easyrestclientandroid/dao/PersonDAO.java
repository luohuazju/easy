package com.sillycat.easyrestclientandroid.dao;

import java.util.List;

import com.sillycat.easyapi.rest.json.Person;

public interface PersonDAO {
	
	public List<Person> all();
	
	public Person get(Integer id);
	
	public void setBaseURL(String baseURL);

}
