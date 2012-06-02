package com.sillycat.easynosql.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sillycat.easynosql.dao.mongodb.repository.RoleRepository;
import com.sillycat.easynosql.dao.mongodb.repository.UserRepository;
import com.sillycat.easynosql.model.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public User create(User user) {
		user.setId(UUID.randomUUID().toString());
		user.getRole().setId(UUID.randomUUID().toString());
		roleRepository.save(user.getRole());
		return userRepository.save(user);
	}

	public User read(User user) {
		return userRepository.findByUsername(user.getUsername());
	}

	public List<User> readAll() {
		return userRepository.findAll();
	}

	public User update(User user) {
		User existingUser = userRepository.findByUsername(user.getUsername());

		if (existingUser == null) {
			return null;
		}

		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.getRole().setRole(user.getRole().getRole());

		roleRepository.save(existingUser.getRole());
		return userRepository.save(existingUser);
	}

	public Boolean delete(User user) {
		User existingUser = userRepository.findByUsername(user.getUsername());

		if (existingUser == null) {
			return false;
		}

		roleRepository.delete(existingUser.getRole());
		userRepository.delete(existingUser);
		return true;
	}

}
