package com.idm.scim.service;

import java.util.List;

import com.idm.scim.dto.User;

/**
 * IUserService includes all business related functions for a user and related entities
 * @author Ramesh Palla
 *
 */
public interface IUserService {

	/**
	 * Return a collection of User object that contain the given filter text.
	 * @param filter a substring that should be contained in returned Users.
	 * @return a collection of matching Users
	 */
	public List<User> filterUsers(String filter);
	
	/**
	 * Creates a user based on provided information
	 * @param user with all user information.
	 * @throws Exception 
	 */
	public void save(User user) throws Exception;
	
	
}
