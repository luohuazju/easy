package com.sillycat.easynosql.dao.neo4j.init;

import org.springframework.beans.factory.annotation.Autowired;

import com.sillycat.easynosql.dao.neo4j.model.Roleneo4j;
import com.sillycat.easynosql.dao.neo4j.model.Userneo4j;
import com.sillycat.easynosql.dao.neo4j.repository.Userneo4jRepository;

public class InitNeo4jService {

	@Autowired
	private Userneo4jRepository userneo4jRepository;

	public void init() {
		if (userneo4jRepository.findByUsername("john") != null) {
			userneo4jRepository.delete(userneo4jRepository
					.findByUsername("john"));
		}

		if (userneo4jRepository.findByUsername("jane") != null) {
			userneo4jRepository.delete(userneo4jRepository
					.findByUsername("jane"));
		}

		// Create new records
		Userneo4j john = new Userneo4j();
		john.setFirstName("John");
		john.setLastName("Smith");
		john.setPassword("111111");
		john.setRoleneo4j(new Roleneo4j(1));
		john.setUsername("john");

		Userneo4j jane = new Userneo4j();
		jane.setFirstName("Jane");
		jane.setLastName("Adams");
		jane.setPassword("111111");
		jane.setRoleneo4j(new Roleneo4j(2));
		jane.setUsername("jane");

		john.getRoleneo4j().setUserneo4j(john);
		jane.getRoleneo4j().setUserneo4j(jane);

		userneo4jRepository.save(john);
		userneo4jRepository.save(jane);

		userneo4jRepository.findByUsername("john").getRoleneo4j().getRole();
	}

}
