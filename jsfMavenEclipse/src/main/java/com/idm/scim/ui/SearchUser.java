package com.idm.scim.ui;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.web.context.annotation.SessionScope;

import com.idm.scim.dto.User;

@Named
@SessionScope
public class SearchUser {

	@Inject
	private User user;
	
	public String execute() {
		
		if(user != null && user.getName().equalsIgnoreCase("Ramesh")) {
			return "search";
		}else {
			return "noresults";
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
