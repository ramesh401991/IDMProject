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

	public String insert(User user) throws Exception;
	
	public String update(User user) throws Exception;
	
	public String delete(User user) throws Exception;
	
	public User getUserByID(long id);
}
