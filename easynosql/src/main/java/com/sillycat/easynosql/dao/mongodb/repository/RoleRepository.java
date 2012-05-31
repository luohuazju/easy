package com.sillycat.easynosql.dao.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sillycat.easynosql.model.Role;

public interface RoleRepository extends MongoRepository<Role, String> {

}
