package com.idm.scim.ui;

import java.util.Date;
import java.util.List;

import com.idm.scim.hibernate.dao.UserDAO;
import com.idm.scim.hibernate.model.User;

public class TestHibernate {

	public static void main(String[] args) {

		UserDAO userDao = new UserDAO();

		/*
		 * User user = new User("Ram", "Palla", "rameshpalla@gmail.com", new Date(),
		 * "Test", "Admin", "Schaff", "77968"); List<User>
		 * allUsersTest=userDao.fetchUsers(); for (User user2 : allUsersTest) {
		 * System.out.println(user2.getFirstName()); }
		 */
		/*
		 * 
		 * user.setFirstName("Ram"); userDao.update(user);
		 * 
		 * User userTest = userDao.getUserByID(user.getId());
		 * 
		 * List<User> allUsersTest = userDao.fetchUsers();
		 * 
		 * 
		 * System.out.println("GetUserByID"+userTest.getFirstName());
		 * 
		 * System.out.println("Get All Users"+allUsersTest.get(0).getFirstName());
		 * 
		 * 
		 *  userDao.deleteUser(user.getId());
		 */
	}

}
