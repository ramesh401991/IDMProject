package com.idm.scim.hibernate.dao;

import java.util.List;

import com.idm.scim.dto.User;

/**
 * IUserDAO includes all CRUD operations related to a user and its entities
 * @author Ramesh Palla
 *
 */
public interface IUserDAO {

	public List<User> fetchUsers();

	public void insert(User user) throws Exception;
	
	public void update(User user) throws Exception;
	
	public void delete(User user) throws Exception;
	
	public List<User> getUserByID(long id);
}
