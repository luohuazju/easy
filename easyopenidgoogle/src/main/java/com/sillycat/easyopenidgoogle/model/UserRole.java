package com.sillycat.easyopenidgoogle.model;

import java.util.ArrayList;
import java.util.List;

public class UserRole {

	private String roleName;

	private String roleAlias;

	private List<UserAuthority> roleAuthorities = new ArrayList<UserAuthority>();

	private String description;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleAlias() {
		return roleAlias;
	}

	public void setRoleAlias(String roleAlias) {
		this.roleAlias = roleAlias;
	}

	public List<UserAuthority> getRoleAuthorities() {
		return roleAuthorities;
	}

	public void setRoleAuthorities(List<UserAuthority> roleAuthorities) {
		this.roleAuthorities = roleAuthorities;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
