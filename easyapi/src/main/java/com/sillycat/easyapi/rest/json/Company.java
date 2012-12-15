package com.sillycat.easyapi.rest.json;
 
import java.util.List;

import org.codehaus.jackson.annotate.JsonManagedReference;

public class Company {

	private Integer id;

	private String companyName;

	private List<Person> persons;
	
	public Company(){
		
	}
	
	public Company(Integer id, String companyName){
		this.id = id;
		this.companyName = companyName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@JsonManagedReference("Company-Person")
	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

}
