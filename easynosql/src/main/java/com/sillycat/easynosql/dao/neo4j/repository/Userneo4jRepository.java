package com.sillycat.easynosql.dao.neo4j.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.sillycat.easynosql.dao.neo4j.model.Userneo4j;

public interface Userneo4jRepository extends GraphRepository<Userneo4j> {
	
	Userneo4j findByUsername(String username);

}
