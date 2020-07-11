package com.idm.scim.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.idm.scim.hibernate.model.User;
import com.idm.scim.hibernate.util.HibernateUtil;

public class UserDao {
	
	//Save Users
	public void insert(User user) {
		Transaction transaction = null;
		
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			
			//Start the transaction
			transaction = session.beginTransaction();
			
			//save User object
			session.save(user);
			
			//commit transaction
			transaction.commit();
			
		}catch(Exception e){
			if(transaction != null) {
				transaction.rollback();
			}
		}
	}
	//get All Users
	@SuppressWarnings("unchecked")
	public List<User> fetchUsers() {
		Transaction transaction = null;
		List<User> users = null;
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			
			//Start the transaction
			transaction = session.beginTransaction();
			
			//get User object
			users = session.createQuery("from User").list();
			
			//commit transaction
			transaction.commit();
			
		}catch(Exception e){
			if(transaction != null) {
				transaction.rollback();
			}
		}
		return users;
	}
	
	
	//get Users By ID
	
	public User getUserByID(long id) {
		Transaction transaction = null;
		User user = null;
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			
			//Start the transaction
			transaction = session.beginTransaction();
			
			//get User object
			user = session.get(User.class,id);
			
			//commit transaction
			transaction.commit();
			
		}catch(Exception e){
			if(transaction != null) {
				transaction.rollback();
			}
		}
		return user;
	}
	
	
	//Update User
	
	public void update(User user) {
		Transaction transaction = null;
		
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			
			//Start the transaction
			transaction = session.beginTransaction();
			
			//save User object
			session.saveOrUpdate(user);
			
			//commit transaction
			transaction.commit();
			
		}catch(Exception e){
			if(transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	//Delete User
	public void delete(long id) {
		Transaction transaction = null;
		try(Session session=HibernateUtil.getSessionFactory().openSession()){
			
			//Start the transaction
			transaction = session.beginTransaction();
			
			//get User object
			session.delete(session.get(User.class, id));
			
			//commit transaction
			transaction.commit();
			
		}catch(Exception e){
			if(transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	
	
}
