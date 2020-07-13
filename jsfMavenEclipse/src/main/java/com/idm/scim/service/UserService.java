package com.idm.scim.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.idm.scim.dto.User;
import com.idm.scim.hibernate.dao.IUserDAO;

@Named
@SessionScoped
public class UserService implements IUserService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2876237483616370326L;

	@Inject
	IUserDAO userDAO;

	private List<User> allUsers;

	@Override
	public List<User> filterUsers(String filter) {
		if (allUsers == null) {
			allUsers = userDAO.fetchUsers();
		}

		ArrayList<User> returnUsers = new ArrayList<User>();

		// filter the List
		for (User user : allUsers) {

			if (user.toString().contains(filter)) {
				// This user matches all criteria and adds to collection
				returnUsers.add(user);
			}

		}

		return returnUsers;
	}

	@Override
	public String save(User user) throws Exception {

		return userDAO.insert(user);
	}

	@Override
	public String update(User user) throws Exception {
		// TODO Auto-generated method stub
		String returnValue = userDAO.update(user);

		allUsers = userDAO.fetchUsers();

		return returnValue;
	}

	@Override
	public String Delete(User user) throws Exception {
		// TODO Auto-generated method stub
		String returnValue = userDAO.delete(user);

		allUsers = userDAO.fetchUsers();

		return returnValue;
	}

}
