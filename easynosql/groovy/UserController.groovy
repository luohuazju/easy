package com.sillycat.easynosql.web;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus

import com.sillycat.easynosql.model.Role
import com.sillycat.easynosql.model.User
import com.sillycat.easynosql.model.dto.UserListDTO
import com.sillycat.easynosql.service.UserService


@Controller
@RequestMapping("/users")
class UserController {
	
	@Autowired
	UserService userService
	
	@RequestMapping(value="/records")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody UserListDTO getUsers() {
		UserListDTO userListDTO = new UserListDTO();
		userListDTO.setUsers(userService.readAll());
		return userListDTO;
	}
	
	@RequestMapping(value="/get")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody User get(@RequestBody User user) {
		return userService.read(user);
	}

	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody User create(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("role") Integer role) {
		Role newRole = new Role();
		newRole.setRole(role);
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(password);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setRole(newRole);
		return userService.create(newUser);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody User update(
			@RequestParam("userName") String userName,
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("role") Integer role) {
		Role existingRole = new Role();
		existingRole.setRole(role);
		
		User existingUser = new User();
		existingUser.setUsername(userName);
		existingUser.setFirstName(firstName);
		existingUser.setLastName(lastName);
		existingUser.setRole(existingRole);
		
		return userService.update(existingUser);
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody Boolean delete(
			@RequestParam("username") String username) {

		User existingUser = new User();
		existingUser.setUsername(username);
		
		return userService.delete(existingUser);
	}
	
}