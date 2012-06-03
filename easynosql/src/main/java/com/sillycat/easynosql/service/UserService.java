package com.sillycat.easynosql.service;

import java.util.List;

import com.sillycat.easynosql.model.User;

public interface UserService {

	public abstract Boolean delete(User user);

	public abstract User update(User user);

	public abstract List<User> readAll();

	public abstract User read(User user);

	public abstract User create(User user);

}
