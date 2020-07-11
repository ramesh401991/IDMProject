package com.idm.scim.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.idm.scim.dto.User;
import com.idm.scim.hibernate.dao.IUserDAO;

@Named("userService")
public class UserService implements IUserService {

	@Inject
	IUserDAO userDAO;

	private List<User> allUsers;

	@Override
	public List<User> filterUsers(String filter) {
		if (allUsers == null) {
			allUsers = userDAO.fetchUsers();
		}

		ArrayList<User> returnUsers = new ArrayList<User>();
		
		//filter the List
		for (User user : allUsers) {
			
			if(user.toString().contains(filter)) {
				//This user matches all criteria and adds to collection
				returnUsers.add(user);
			}
			
		}
		
		return returnUsers;
	}

}
