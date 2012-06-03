package com.sillycat.easynosql.dao.mongodb.init;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.sillycat.easynosql.dao.mongodb.model.Rolemongo;
import com.sillycat.easynosql.dao.mongodb.model.Usermongo;

public class InitMongoService {

	@Autowired
	private MongoTemplate mongoTemplate;

	public void init() {
		// Drop existing collections
		mongoTemplate.dropCollection("rolemongo");
		mongoTemplate.dropCollection("usermongo");

		// Create new records
		Rolemongo adminRolemongo = new Rolemongo();
		adminRolemongo.setId(UUID.randomUUID().toString());
		adminRolemongo.setRole(1);

		Rolemongo userRolemongo = new Rolemongo();
		userRolemongo.setId(UUID.randomUUID().toString());
		userRolemongo.setRole(2);

		Usermongo john = new Usermongo();
		john.setId(UUID.randomUUID().toString());
		john.setFirstName("John");
		john.setLastName("Smith");
		john.setPassword("111111");
		john.setRolemongo(adminRolemongo);
		john.setUsername("john");

		Usermongo jane = new Usermongo();
		jane.setId(UUID.randomUUID().toString());
		jane.setFirstName("Jane");
		jane.setLastName("Adams");
		jane.setPassword("111111");
		jane.setRolemongo(userRolemongo);
		jane.setUsername("jane");

		// Insert to db
		mongoTemplate.insert(john, "usermongo");
		mongoTemplate.insert(jane, "usermongo");
		mongoTemplate.insert(adminRolemongo, "rolemongo");
		mongoTemplate.insert(userRolemongo, "rolemongo");
		
	}

}
