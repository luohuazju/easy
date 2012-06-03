package com.sillycat.easynosql.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sillycat.easynosql.dao.mongodb.model.Usermongo;

public interface UsermongoRepository extends MongoRepository<Usermongo, String> {

	Usermongo findByUsername(String username);
	
}
