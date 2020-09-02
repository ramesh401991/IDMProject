package com.idm.scim.ui;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.idm.scim.dto.User;
import com.idm.scim.service.IUserService;

@Named
@Scope("session")
public class UpdateUser {

	@Inject
	private User user;
	
	@Inject
	private IUserService userService;
	
	public String execute() {
		String returnValue = "fail";
		try {
			returnValue = userService.update(user);
			if(returnValue.contains("fail")) {
				returnValue = "fail";	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
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
