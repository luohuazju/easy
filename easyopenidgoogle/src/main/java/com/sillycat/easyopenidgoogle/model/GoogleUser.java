package com.sillycat.easyopenidgoogle.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class GoogleUser implements UserDetails{
	
	private static final long serialVersionUID = -9200542003695592716L;

	private String password;

	private String username;

	private boolean accountNonExpired = true;

	private boolean accountNonLocked = true;

	private boolean credentialsNonExpired = true;

	private boolean enabled = true;
	
	private String title = "SUPERMAN";

	private List<UserRole> userRoles = new ArrayList<UserRole>();
	
	public String getTitle(){
		return this.title;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		if (this.getUserRoles() != null && !this.getUserRoles().isEmpty()) {
			for (int i = 0; i < this.getUserRoles().size(); i++) {
				UserRole userRole = this.getUserRoles().get(i);
				if (userRole != null && userRole.getRoleAuthorities() != null
						&& !userRole.getRoleAuthorities().isEmpty()) {
					for (int j = 0; j < userRole.getRoleAuthorities().size(); j++) {
						UserAuthority userAuthority = userRole
								.getRoleAuthorities().get(j);
						grantedAuthorities.addAll(AuthorityUtils
								.createAuthorityList(userAuthority
										.getAuthorityName()));
					}
				}
			}
		}
		return grantedAuthorities;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

}
