package com.sillycat.easynosql.dao.neo4j.model;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "HAS_ROLENEO4J")
public class Userneo4jRoleneo4jRelationship {

	private String description;

	@StartNode
	private Userneo4j userneo4j;

	@EndNode
	private Roleneo4j roleneo4j;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Userneo4j getUserneo4j() {
		return userneo4j;
	}

	public void setUserneo4j(Userneo4j userneo4j) {
		this.userneo4j = userneo4j;
	}

	public Roleneo4j getRoleneo4j() {
		return roleneo4j;
	}

	public void setRoleneo4j(Roleneo4j roleneo4j) {
		this.roleneo4j = roleneo4j;
	}
	
}
