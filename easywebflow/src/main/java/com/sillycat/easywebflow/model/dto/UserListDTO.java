package com.sillycat.easywebflow.model.dto;

import java.util.List;

import org.springframework.security.core.userdetails.User;

public class UserListDTO {

	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
