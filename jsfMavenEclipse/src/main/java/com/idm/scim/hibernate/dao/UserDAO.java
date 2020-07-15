package com.idm.scim.hibernate.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.idm.scim.dto.Credentials;
import com.idm.scim.dto.User;
import com.idm.scim.hibernate.util.HibernateUtil;

@Named("userDAO")
@SessionScoped
public class UserDAO implements IUserDAO,Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7835183728033049296L;
	HibernateUtil hibernateUtil = new HibernateUtil();
	
	//Save Users
	public String insert(User user) {
		Transaction transaction = null;
		String result = "success";
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			
			//Start the transaction
			transaction = session.beginTransaction();
			
			//save User object
			com.idm.scim.hibernate.model.User entityUser = hibernateUtil.mapDTOToEntity(user);
			session.save(entityUser);
			
			//commit transaction
			transaction.commit();
			
		}catch(Exception e){
			result = "fail";
			if(transaction != null) {
				transaction.rollback();
			}
		}
		return result;
	}
	//get All Users
	@SuppressWarnings("unchecked")
	public List<User> fetchUsers() {
		Transaction transaction = null;
		List<com.idm.scim.hibernate.model.User> users = null;
		List<User> dtoUsers = new ArrayList<User>();
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			
			//Start the transaction
			transaction = session.beginTransaction();
			
			//get User object
			users = session.createQuery("from User").list();
			
			User dtoUser;
			
			
			for(com.idm.scim.hibernate.model.User user:users) {
				dtoUser = new User();
				dtoUser = hibernateUtil.mapEntityToDTO(user);
				dtoUsers.add(dtoUser);
			}
			
			//commit transaction
			transaction.commit();
			
		}catch(Exception e){
			if(transaction != null) {
				transaction.rollback();
			}
		}
		return dtoUsers;
	}
	
	
	//get Users By ID
	
	public User getUserByID(long id) {
		Transaction transaction = null;
		com.idm.scim.hibernate.model.User user = null;
		User dtoUser = null;
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			
			//Start the transaction
			transaction = session.beginTransaction();
			
			//get User object
			user = session.get(com.idm.scim.hibernate.model.User.class,id);
			
			dtoUser = hibernateUtil.mapEntityToDTO(user);
			
			//commit transaction
			transaction.commit();
			
		}catch(Exception e){
			if(transaction != null) {
				transaction.rollback();
			}
		}
		return dtoUser;
	}
	
	
	//Update User
	
	public String update(User user) {
		Transaction transaction = null;
		String result = "success";
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			
			//Start the transaction
			transaction = session.beginTransaction();
			
			com.idm.scim.hibernate.model.User entityUser = hibernateUtil.mapDTOToEntity(user);
			
			//save User object
			session.saveOrUpdate(entityUser);
			
			//commit transaction
			transaction.commit();
			
		}catch(Exception e){
			result = "fail";
			if(transaction != null) {
				transaction.rollback();
			}
		}
		
		return result;
	}
	
	//Delete User
	public String delete(User user) {
		Transaction transaction = null;
		String result = "success";
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			
			//Start the transaction
			transaction = session.beginTransaction();
			
			//get User object
			session.delete(session.get(com.idm.scim.hibernate.model.User.class, user.getId()));
			
			//commit transaction
			transaction.commit();
			
		}catch(Exception e){
			result = "fail";
			if(transaction != null) {
				transaction.rollback();
			}
		}
		return result;
	}
	@Override
	public boolean validateUser(Credentials creds) {
		Transaction transaction = null;
		com.idm.scim.hibernate.model.User user = null;

		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			
			//Start the transaction
			transaction = session.beginTransaction();
			
			//get User object
			user = (com.idm.scim.hibernate.model.User)session.createQuery("FROM User U WHERE U.userName = :userName").setParameter("userName", creds.getUsername()).uniqueResult();
			
			if(user!=null && user.getPassword().equals(creds.getPassword())) {
				return true;
			}
			
			//commit transaction
			transaction.commit();
			
		}catch(Exception e){
			if(transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}		
		
		return false;
	}	
	
}
