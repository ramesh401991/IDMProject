package com.idm.scim.ui;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.web.context.annotation.SessionScope;

import com.idm.scim.dto.User;
import com.idm.scim.service.IUserService;

@Named
@SessionScope
public class SearchUser {

	@Inject
	private User user;
	
	@Inject
	private IUserService userService;
	
	public String execute() {
		
		if(user != null && user.getName().equalsIgnoreCase("Ramesh")) {
			return "search";
		}else {
			return "noresults";
		}
	}
	
	public List<User> completeUsers(String Query){
		List<User> filterUsers = userService.filterUsers(Query);
		return filterUsers;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
