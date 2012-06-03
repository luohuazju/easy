package com.sillycat.easynosql.model.convert;

import com.sillycat.easynosql.dao.mongodb.model.Rolemongo;
import com.sillycat.easynosql.model.Role;

public class RoleConvert {
	
	public static Role convertRolemongo2Role(Rolemongo rolemongo){
		Role role = null;
		if(rolemongo != null){
			role = new Role();
			role.setId(rolemongo.getId());
			role.setRole(rolemongo.getRole());
		}
		return role;
	}

	public static Rolemongo convertRole2Rolemongo(Role role) {
		Rolemongo rolemongo = null;
		if (role != null) {
			rolemongo = new Rolemongo();
			rolemongo.setId(role.getId());
			rolemongo.setRole(role.getRole());
		}
		return rolemongo;
	}

}
