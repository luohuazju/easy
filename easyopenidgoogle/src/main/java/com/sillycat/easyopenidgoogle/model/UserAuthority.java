package com.sillycat.easyopenidgoogle.model;

import java.io.Serializable;

public class UserAuthority implements Serializable {
	
	
	private static final long serialVersionUID = 992770454626010614L;

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
