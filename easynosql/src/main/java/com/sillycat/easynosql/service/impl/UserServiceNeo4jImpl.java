package com.sillycat.easynosql.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;

import com.sillycat.easynosql.dao.neo4j.model.Userneo4j;
import com.sillycat.easynosql.dao.neo4j.repository.Roleneo4jRepository;
import com.sillycat.easynosql.dao.neo4j.repository.Userneo4jRepository;
import com.sillycat.easynosql.model.User;
import com.sillycat.easynosql.model.convert.UserConvert;
import com.sillycat.easynosql.service.UserService;

public class UserServiceNeo4jImpl implements UserService{

	@Autowired
	private Userneo4jRepository userneo4jRepository;

	@Autowired
	private Roleneo4jRepository roleneo4jRepository;

	public User create(User user) {
		Userneo4j existingUser = userneo4jRepository.findByUsername(user
				.getUsername());

		if (existingUser != null) {
			throw new RuntimeException("Record already exists!");
		}
		Userneo4j saveUser = UserConvert.convertUser2Userneo4j(user);
		saveUser.getRoleneo4j().setUserneo4j(saveUser);
		userneo4jRepository.save(saveUser);
		return UserConvert.convertUserneo4j2User(saveUser);
	}

	public User read(User user) {
		Userneo4j existingUser = userneo4jRepository.findByUsername(user
				.getUsername());
		user = UserConvert.convertUserneo4j2User(existingUser);
		return user;
	}

	public List<User> readAll() {
		List<User> users = new ArrayList<User>();

		EndResult<Userneo4j> results = userneo4jRepository.findAll();
		for (Userneo4j r : results) {
			users.add(UserConvert.convertUserneo4j2User(r));
		}

		return users;
	}

	public User update(User user) {
		Userneo4j existingUser = userneo4jRepository.findByUsername(user
				.getUsername());

		if (existingUser == null) {
			return null;
		}

		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.getRoleneo4j().setRole(user.getRole().getRole());

		roleneo4jRepository.save(existingUser.getRoleneo4j());
		userneo4jRepository.save(existingUser);
		return UserConvert.convertUserneo4j2User(existingUser);
	}

	public Boolean delete(User user) {
		Userneo4j existingUser = userneo4jRepository.findByUsername(user
				.getUsername());

		if (existingUser == null) {
			return false;
		}

		userneo4jRepository.delete(existingUser);
		return true;
	}

}
