package com.sillycat.easynosql.model.convert;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

import com.sillycat.easynosql.dao.mongodb.model.Usermongo;
import com.sillycat.easynosql.dao.neo4j.model.Userneo4j;
import com.sillycat.easynosql.model.User;

public class UserConvert {

	private static DozerBeanMapper mapper = new DozerBeanMapper();

	public static User convertUserneo4j2User(Userneo4j userneo4j) {
		User user = null;
		if (userneo4j != null) {
			user = mapper.map(userneo4j, User.class);
			if (userneo4j.getRoleneo4j() != null) {
				user.setRole(RoleConvert.convertRoleneo4j2Role(userneo4j
						.getRoleneo4j()));
			}
		}
		return user;
	}

	public static Userneo4j convertUser2Userneo4j(User user) {
		Userneo4j userneo4j = null;
		if (user != null) {
			userneo4j = mapper.map(user, Userneo4j.class);
			if (user.getRole() != null) {
				userneo4j.setRoleneo4j(RoleConvert.convertRole2Roleneo4j(user
						.getRole()));
			}
		}
		return userneo4j;
	}

	public static List<User> convertListUsermongo2User(
			List<Usermongo> usermongos) {
		List<User> list = null;
		if (usermongos != null && !usermongos.isEmpty()) {
			list = new ArrayList<User>(usermongos.size());
			for (int i = 0; i < usermongos.size(); i++) {
				Usermongo usermongo = usermongos.get(i);
				list.add(convertUsermongo2User(usermongo));
			}
		}
		return list;
	}

	public static User convertUsermongo2User(Usermongo usermongo) {
		User user = null;
		if (usermongo != null) {
			user = mapper.map(usermongo, User.class);
			if (usermongo.getRolemongo() != null) {
				user.setRole(RoleConvert.convertRolemongo2Role(usermongo
						.getRolemongo()));
			}
		}
		return user;
	}

	public static Usermongo convertUser2Usermongo(User user) {
		Usermongo usermongo = null;
		if (user != null) {
			usermongo = mapper.map(user, Usermongo.class);
			if (user.getRole() != null) {
				usermongo.setRolemongo(RoleConvert.convertRole2Rolemongo(user
						.getRole()));
			}
		}
		return usermongo;
	}

}
