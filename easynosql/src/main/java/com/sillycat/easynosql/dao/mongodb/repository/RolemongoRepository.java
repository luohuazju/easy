package com.sillycat.easynosql.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sillycat.easynosql.dao.mongodb.model.Rolemongo;

public interface RolemongoRepository extends MongoRepository<Rolemongo, String> {

}
