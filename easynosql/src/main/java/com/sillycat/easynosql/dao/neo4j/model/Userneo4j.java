package com.sillycat.easynosql.dao.neo4j.model;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Userneo4j {

	@GraphId
	private Long id;
	
	private String firstName;
	private String lastName;
	
	@Indexed
	private String username;
	private String password;
	
	@Fetch @RelatedTo(type = "HAS_ROLENEO4J")
	private Roleneo4j roleneo4j;
	
	public Userneo4j() {}
	
	public Userneo4j(String username) {
		this.username = username;
	}
	
	public Userneo4j(String username, String firstName, String lastName, Roleneo4j roleneo4j) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roleneo4j = roleneo4j;
	}
	
	public Userneo4j(String username, String password, String firstName, String lastName, Roleneo4j roleneo4j) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roleneo4j = roleneo4j;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Roleneo4j getRoleneo4j() {
		return roleneo4j;
	}

	public void setRoleneo4j(Roleneo4j roleneo4j) {
		this.roleneo4j = roleneo4j;
	}
	
	
}
