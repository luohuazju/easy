package com.sillycat.easynosql.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.sillycat.easynosql.model.Role;
import com.sillycat.easynosql.model.User;
import com.sillycat.easynosql.service.UserService;

public class UserServiceRedisImpl implements UserService{

	@Autowired
	private RedisTemplate<String, String> template;

	public User create(User user) {
		String key = "user"+user.getUsername();
		String id = UUID.randomUUID().toString();
		//use hash structure to store the columns
		template.opsForHash().put(key, "id", id);
		template.opsForHash().put(key, "firstName", user.getFirstName());
		template.opsForHash().put(key, "lastName", user.getLastName());
		template.opsForHash().put(key, "username", user.getUsername());
		template.opsForHash().put(key, "password", user.getPassword());
		template.opsForHash().put(key, "role", user.getRole().getRole().toString());
		//use set structure to store the users
		template.opsForSet().add("user", key);
		user.setId(id);
		return user;
	}

	public User read(User user) {
		String key = "user"+user.getUsername();
		String existingRecord = (String) template.opsForHash().get(key, "id");

		if (existingRecord == null) {
			return null;
		}
		User returnUser = new User();
		returnUser.setId((String) template.opsForHash().get(key, "id"));
		returnUser.setFirstName((String) template.opsForHash().get(key, "firstName"));
		returnUser.setLastName((String) template.opsForHash().get(key, "lastName"));
		returnUser.setPassword((String) template.opsForHash().get(key, "password"));
		returnUser.setUsername((String) template.opsForHash().get(key, "username"));
		
		Role role = new Role();
		role.setRole(Integer.valueOf((String) template.opsForHash().get(key, "role")));
		returnUser.setRole(role);
		return user;
	}

	public List<User> readAll() {
		List<User> users = new ArrayList<User>();
		
		Collection<String> fieldKeys = new HashSet<String>();
		fieldKeys.add("id");
		fieldKeys.add("firstName");
		fieldKeys.add("lastName");
		fieldKeys.add("username");
		fieldKeys.add("password");
		fieldKeys.add("role");

		//fetch all the key from set
		Collection<String> keys = template.opsForSet().members("user");
		for (String key: keys) {
			User user = new User();
			//find the value with key/column name
			user.setId((String) template.opsForHash().get(key, "id"));
			user.setFirstName((String) template.opsForHash().get(key, "firstName"));
			user.setLastName((String) template.opsForHash().get(key, "lastName"));
			user.setPassword((String) template.opsForHash().get(key, "password"));
			user.setUsername((String) template.opsForHash().get(key, "username"));
			
			Role role = new Role();
			role.setRole(Integer.valueOf((String) template.opsForHash().get(key, "role")));
			user.setRole(role);
			
			users.add(user);
		}
		
		return users;
	}

	public User update(User user) {
		String key = "user"+user.getUsername();
		String existingRecord = (String) template.opsForHash().get(key, "id");

		if (existingRecord == null) {
			return null;
		}

		template.opsForHash().put(key, "firstName", user.getFirstName());
		template.opsForHash().put(key, "lastName", user.getLastName());
		template.opsForHash().put(key, "role", user.getRole().getRole().toString());

		return user;
	}

	public Boolean delete(User user) {
		String key = "user"+user.getUsername();
		template.opsForHash().delete(key, "id");
		template.opsForHash().delete(key, "firstName");
		template.opsForHash().delete(key, "lastName");
		template.opsForHash().delete(key, "username");
		template.opsForHash().delete(key, "password");
		template.opsForHash().delete(key, "role");

		String existingRecord = (String) template.opsForHash().get(key, "id");
		Boolean existingMember = template.opsForSet().remove("user", key);
		
		if (existingRecord != null) {
			return false;
		}
		
		if (existingMember == false) {
			return false;
		}
		
		return true;
	}
	
}
