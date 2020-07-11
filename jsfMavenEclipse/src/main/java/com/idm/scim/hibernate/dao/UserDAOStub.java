package com.idm.scim.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import com.idm.scim.dto.User;

@Named("userDAO")
public class UserDAOStub implements IUserDAO {

	@Override
	public List<User> fetchUsers() {
		// TODO Auto-generated method stub
		
		List<User> allUser = new ArrayList<User>();
		
		User user1 = new User();
		user1.setName("Ramesh");
		allUser.add(user1);
		
		User user2 = new User();
		user2.setName("Eswar");
		allUser.add(user2);
		
		User user3 = new User();
		user3.setName("Manikanta");
		allUser.add(user3);
		
		User user4 = new User();
		user4.setName("Sushma");
		allUser.add(user4);
		
		User user5 = new User();
		user5.setName("Venkatarao");
		allUser.add(user5);
		
		return allUser;
	}

	@Override
	public void insert(User user) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(User user) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(User user) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> getUserByID(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
