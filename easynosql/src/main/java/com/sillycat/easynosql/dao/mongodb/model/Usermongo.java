package com.sillycat.easynosql.dao.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Usermongo {

	@Id
	private String id;

	private String firstName;
	
	private String lastName;

	private String username;
	
	private String password;
	
	@DBRef
	private Rolemongo rolemongo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Rolemongo getRolemongo() {
		return rolemongo;
	}

	public void setRolemongo(Rolemongo rolemongo) {
		this.rolemongo = rolemongo;
	}
	
	
}
