package com.sillycat.easynosql.service;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sillycat.easynosql.model.Role;
import com.sillycat.easynosql.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/test-context.xml" })
public class UserServiceTest {

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	private User item;

	@Before
	public void before() {
		item = this.getItem();
	}

	@After
	public void after() {
		if (item != null && item.getId() != null) {
			userService.delete(item);
		}
	}

	@Test
	public void dumy() {
		Assert.assertTrue(true);
	}

	@Test
	public void create() {
		item = userService.create(item);
		Assert.assertNotNull(item);
		Assert.assertNotNull(item.getId());
	}

	@Test
	public void read() {
		item.setUsername("john");
		User user = userService.read(item);
		Assert.assertNotNull(user);
	}

	private User getItem() {
		User item = new User();
		item.setFirstName("item1");
		item.setLastName("item1");
		item.setPassword("111111");
		item.setUsername("testcase2");

		Role role = new Role();
		role.setRole(1);

		item.setRole(role);
		return item;
	}

}
