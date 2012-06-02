package com.sillycat.easynosql.dao.mongodb.init;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.sillycat.easynosql.model.Role;
import com.sillycat.easynosql.model.User;

public class InitMongoService {

	@Autowired
	private MongoTemplate mongoTemplate;

	public void init() {
		// Drop existing collections
		mongoTemplate.dropCollection("role");
		mongoTemplate.dropCollection("user");

		// Create new records
		Role adminRole = new Role();
		adminRole.setId(UUID.randomUUID().toString());
		adminRole.setRole(1);

		Role userRole = new Role();
		userRole.setId(UUID.randomUUID().toString());
		userRole.setRole(2);

		User john = new User();
		john.setId(UUID.randomUUID().toString());
		john.setFirstName("John");
		john.setLastName("Smith");
		john.setPassword("111111");
		john.setRole(adminRole);
		john.setUsername("john");

		User jane = new User();
		jane.setId(UUID.randomUUID().toString());
		jane.setFirstName("Jane");
		jane.setLastName("Adams");
		jane.setPassword("111111");
		jane.setRole(userRole);
		jane.setUsername("jane");

		// Insert to db
		mongoTemplate.insert(john, "user");
		mongoTemplate.insert(jane, "user");
		mongoTemplate.insert(adminRole, "role");
		mongoTemplate.insert(userRole, "role");
		
	}

}
