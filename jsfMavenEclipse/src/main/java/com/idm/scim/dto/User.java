package com.idm.scim.dto;

import java.io.Serializable;

import javax.inject.Named;

import org.springframework.web.context.annotation.SessionScope;

@Named
@SessionScope
public class User implements Serializable {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
}
