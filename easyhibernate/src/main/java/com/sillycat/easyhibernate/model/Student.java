package com.sillycat.easyhibernate.model;

import javax.persistence.Version;
import org.hibernate.annotations.Entity;

@Entity
public class Student {
	
	private Integer id;
	
	private String firstName;
	
	private String lastName;
	
	private Integer version;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
}
