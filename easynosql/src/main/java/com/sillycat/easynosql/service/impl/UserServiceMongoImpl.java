package com.sillycat.easynosql.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.sillycat.easynosql.dao.mongodb.model.Usermongo;
import com.sillycat.easynosql.dao.mongodb.repository.RolemongoRepository;
import com.sillycat.easynosql.dao.mongodb.repository.UsermongoRepository;
import com.sillycat.easynosql.model.User;
import com.sillycat.easynosql.model.convert.RoleConvert;
import com.sillycat.easynosql.model.convert.UserConvert;
import com.sillycat.easynosql.service.UserService;

public class UserServiceMongoImpl implements UserService{

	@Autowired
	private UsermongoRepository usermongoRepository;

	@Autowired
	private RolemongoRepository rolemongoRepository;

	public User create(User user) {
		user.setId(UUID.randomUUID().toString());
		user.getRole().setId(UUID.randomUUID().toString());
		rolemongoRepository.save(RoleConvert.convertRole2Rolemongo(user
				.getRole()));
		usermongoRepository.save(UserConvert.convertUser2Usermongo(user));
		return user;
	}

	public User read(User user) {
		Usermongo usermongo = usermongoRepository.findByUsername(user.getUsername());
		user = UserConvert.convertUsermongo2User(usermongo);
		return user;
	}

	public List<User> readAll() {
		List<Usermongo> list = usermongoRepository.findAll();
		List<User> users = UserConvert.convertListUsermongo2User(list);
		return users;
	}

	public User update(User user) {
		Usermongo existingUser = usermongoRepository.findByUsername(user.getUsername());

		if (existingUser == null) {
			return null;
		}

		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.getRolemongo().setRole(user.getRole().getRole());

		rolemongoRepository.save(existingUser.getRolemongo());
		usermongoRepository.save(existingUser);
		return user;
	}

	public Boolean delete(User user) {
		Usermongo existingUser = usermongoRepository.findByUsername(user.getUsername());

		if (existingUser == null) {
			return false;
		}

		rolemongoRepository.delete(existingUser.getRolemongo());
		usermongoRepository.delete(existingUser);
		return true;
	}

}
