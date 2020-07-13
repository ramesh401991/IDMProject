package com.idm.scim.hibernate.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.schema.Action;

import com.idm.scim.hibernate.model.User;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();

				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				settings.put(Environment.URL, "jdbc:mysql://13.59.182.84:3306/scimDB?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC");
				settings.put(Environment.USER, "scimUser");
				settings.put(Environment.PASS, "P@ssw0rd");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

				settings.put(Environment.SHOW_SQL, "true");

				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

				settings.put(Environment.HBM2DDL_AUTO, Action.VALIDATE);

				configuration.setProperties(settings);

				configuration.addAnnotatedClass(User.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}

	public User mapDTOToEntity(com.idm.scim.dto.User user) {

		User returnUser = new User();

		returnUser.setAddress(user.getAddress());
		returnUser.setDob(user.getDob());
		returnUser.setEmail(user.getEmail());
		returnUser.setFirstName(user.getFirstName());
		returnUser.setLastName(user.getLastName());
		returnUser.setMobile(user.getMobile());
		returnUser.setPassword(user.getPassword());
		returnUser.setRole(user.getRole());
		returnUser.setId(user.getId());

		return returnUser;
	}

	public com.idm.scim.dto.User mapEntityToDTO(User user) {

		com.idm.scim.dto.User returnUser = new com.idm.scim.dto.User();

		returnUser.setAddress(user.getAddress());
		returnUser.setDob(user.getDob());
		returnUser.setEmail(user.getEmail());
		returnUser.setFirstName(user.getFirstName());
		returnUser.setLastName(user.getLastName());
		returnUser.setMobile(user.getMobile());
		returnUser.setRole(user.getRole());
		returnUser.setId(user.getId());

		return returnUser;
	}

}
