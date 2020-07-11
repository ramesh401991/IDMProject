package com.idm.scim.dto;

import javax.inject.Named;

import org.springframework.web.context.annotation.SessionScope;

@Named
@SessionScope
public class User {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
