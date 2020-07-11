package com.idm.scim.ui;

import java.util.List;

import com.idm.scim.hibernate.dao.UserDao;
import com.idm.scim.hibernate.model.User;

public class TestHibernate {

	public static void main(String[] args) {
		
		UserDao userDao = new UserDao();
		
		User user = new User("Ramesh","Palla","ramesh40palla@gmail.com");
		userDao.saveUser(user);
		
		user.setFirstName("Ram");
		userDao.updateUser(user);
		
		User userTest = userDao.getUserByID(user.getId());
		
		List<User> allUsersTest = userDao.getAllUsers();
		
		System.out.println("GetUserByID"+userTest.getFirstName());
		
		System.out.println("Get All Users"+allUsersTest.get(0).getFirstName());
		
		//userDao.deleteUser(user.getId());
		
	}
	
	
}
