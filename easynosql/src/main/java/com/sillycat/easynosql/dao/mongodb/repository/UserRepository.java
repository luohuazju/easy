package com.sillycat.easynosql.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sillycat.easynosql.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findByUsername(String username);
	
}
