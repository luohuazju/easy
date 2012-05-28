package com.sillycat.easyopenidgoogle.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import com.sillycat.easyopenidgoogle.model.GoogleUser;
import com.sillycat.easyopenidgoogle.model.UserAuthority;
import com.sillycat.easyopenidgoogle.model.UserRole;

public class OpenIdUserDetailsService implements UserDetailsService,
		AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

	private final Map<String, GoogleUser> registeredUsers = new HashMap<String, GoogleUser>();

	//private static final List<GrantedAuthority> DEFAULT_AUTHORITIES = AuthorityUtils
	//		.createAuthorityList("ROLE_USER");

	public UserDetails loadUserDetails(OpenIDAuthenticationToken openIDToken)
			throws UsernameNotFoundException {
		String id = openIDToken.getIdentityUrl();
		System.out.println("identy = " + id);
		String email = null;
		String firstName = null;
		String lastName = null;
		String fullName = null;
		List<OpenIDAttribute> attributes = openIDToken.getAttributes();
		for (OpenIDAttribute attribute : attributes) {
			if (attribute.getName().equals("email")) {
				email = attribute.getValues().get(0);
				System.out.println("email = " + email);
			}
			if (attribute.getName().equals("firstName")) {
				firstName = attribute.getValues().get(0);
				System.out.println("firstName = " + firstName);
			}
			if (attribute.getName().equals("lastName")) {
				lastName = attribute.getValues().get(0);
				System.out.println("lastName = " + lastName);
			}
			if (attribute.getName().equals("fullname")) {
				fullName = attribute.getValues().get(0);
				System.out.println("fullName = " + fullName);
			}
		}
		GoogleUser user = new GoogleUser();
		user.setUsername(email);

		UserRole userRole = new UserRole();
		UserAuthority userAuthority = new UserAuthority();
		userAuthority.setAuthorityAlias("Access the main page!");
		userAuthority.setAuthorityName("ROLE_USER");
		userRole.getRoleAuthorities().add(userAuthority);
		user.getUserRoles().add(userRole);
		
		registeredUsers.put(id, user);
		return user;
	}

	public UserDetails loadUserByUsername(String id)
			throws UsernameNotFoundException {
		UserDetails user = registeredUsers.get(id);

		if (user == null) {
			throw new UsernameNotFoundException(id);
		}

		return user;
	}

}
