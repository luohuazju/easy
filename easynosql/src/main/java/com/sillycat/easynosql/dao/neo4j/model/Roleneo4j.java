package com.sillycat.easynosql.dao.neo4j.model;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Roleneo4j {

	@GraphId
	private Long id;
	private Userneo4j userneo4j;
	private Integer role;

	public Roleneo4j() {
	}

	public Roleneo4j(Integer role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Userneo4j getUserneo4j() {
		return userneo4j;
	}

	public void setUserneo4j(Userneo4j userneo4j) {
		this.userneo4j = userneo4j;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

}
