package com.idm.scim.ui;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.idm.scim.dto.User;
import com.idm.scim.service.IUserService;

@Named
@Scope("session")
public class SearchUser {


	@Inject
	private User user;
	
	@Inject
	private IUserService userService;
	
	public String execute() {
		
		List<User> filterUsers = userService.filterUsers(user.getFirstName());
		
		if(!filterUsers.isEmpty()) {
			this.user.setAddress(filterUsers.get(0).getAddress());
			this.user.setDob(filterUsers.get(0).getDob());
			this.user.setEmail(filterUsers.get(0).getEmail());
			this.user.setFirstName(filterUsers.get(0).getFirstName());
			this.user.setLastName(filterUsers.get(0).getLastName());
			this.user.setMobile(filterUsers.get(0).getMobile());
			this.user.setPassword(filterUsers.get(0).getPassword());
			this.user.setRole(filterUsers.get(0).getRole());
			this.user.setId(filterUsers.get(0).getId());
			return "search";
		}else {
			return "noresults";
		}
	}
	
	/*
	 * public List<User> completeUsers(String Query){ List<User> filterUsers =
	 * userService.filterUsers(Query); return filterUsers; }
	 */

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
