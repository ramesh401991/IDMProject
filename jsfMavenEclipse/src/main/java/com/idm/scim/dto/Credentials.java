package com.idm.scim.dto;

import java.io.Serializable;

public class Credentials implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5865463826816745934L;
	private String username;
    private String password;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}