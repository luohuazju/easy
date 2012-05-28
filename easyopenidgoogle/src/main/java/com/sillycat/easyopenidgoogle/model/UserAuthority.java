package com.sillycat.easyopenidgoogle.model;

public class UserAuthority {

	private String authorityName;

	private String authorityAlias;

	private String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public String getAuthorityAlias() {
		return authorityAlias;
	}

	public void setAuthorityAlias(String authorityAlias) {
		this.authorityAlias = authorityAlias;
	}

}
