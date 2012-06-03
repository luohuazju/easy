package com.sillycat.easynosql.model.convert;

import org.dozer.DozerBeanMapper;

import com.sillycat.easynosql.dao.mongodb.model.Rolemongo;
import com.sillycat.easynosql.dao.neo4j.model.Roleneo4j;
import com.sillycat.easynosql.model.Role;

public class RoleConvert {

	private static DozerBeanMapper mapper = new DozerBeanMapper();
	
	
	public static Role convertRoleneo4j2Role(Roleneo4j roleneo4j){
		Role role = null;
		if(roleneo4j != null){
			role = mapper.map(roleneo4j, Role.class);
		}
		return role;
	}
	
	public static Roleneo4j convertRole2Roleneo4j(Role role) {
		Roleneo4j roleneo4j = null;
		if (role != null) {
			roleneo4j = mapper.map(role, Roleneo4j.class);
		}
		return roleneo4j;
	}

	public static Role convertRolemongo2Role(Rolemongo rolemongo) {
		Role role = null;
		if (rolemongo != null) {
			role = mapper.map(rolemongo, Role.class);
		}
		return role;
	}
	
	public static Rolemongo convertRole2Rolemongo(Role role) {
		Rolemongo rolemongo = null;
		if (role != null) {
			rolemongo = mapper.map(role, Rolemongo.class);
		}
		return rolemongo;
	}

	

}
