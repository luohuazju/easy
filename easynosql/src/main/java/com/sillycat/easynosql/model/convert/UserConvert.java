package com.sillycat.easynosql.model.convert;

import java.util.ArrayList;
import java.util.List;

import com.sillycat.easynosql.dao.mongodb.model.Usermongo;
import com.sillycat.easynosql.model.User;

public class UserConvert {
	
	public static List<User> convertListUsermongo2User(List<Usermongo> usermongos){
		List<User> list = null;
		if(usermongos != null && !usermongos.isEmpty()){
			list = new ArrayList<User>(usermongos.size());
			for(int i = 0;i<usermongos.size();i++){
				Usermongo usermongo = usermongos.get(i);
				list.add(convertUsermongo2User(usermongo));
			}
		}
		return list;
	}
	
	public static User convertUsermongo2User(Usermongo usermongo){
		User user = null;
		if(usermongo != null){
			user = new User();
			user.setFirstName(usermongo.getFirstName());
			user.setId(usermongo.getId());
			user.setLastName(usermongo.getLastName());
			user.setPassword(usermongo.getPassword());
			user.setUsername(usermongo.getUsername());
			if(usermongo.getRolemongo() != null){
				user.setRole(RoleConvert.convertRolemongo2Role(usermongo.getRolemongo()));
			}
		}
		return user;
	}
	
	public static Usermongo convertUser2Usermongo(User user){
		Usermongo usermongo = null;
		if(user != null){
			usermongo = new Usermongo();
			usermongo.setFirstName(user.getFirstName());
			usermongo.setId(user.getId());
			usermongo.setLastName(user.getLastName());
			usermongo.setPassword(user.getPassword());
			usermongo.setUsername(user.getUsername());
			if(user.getRole() != null){
				usermongo.setRolemongo(RoleConvert.convertRole2Rolemongo(user.getRole()));
			}
		}
		return usermongo;
	}

}
