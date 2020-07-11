package com.idm.scim.ui;

import javax.inject.Named;

import org.springframework.web.context.annotation.SessionScope;

@Named
@SessionScope
public class ApplicationInfo {

	private String slogan="Welcome to Sample Application";

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	
}
