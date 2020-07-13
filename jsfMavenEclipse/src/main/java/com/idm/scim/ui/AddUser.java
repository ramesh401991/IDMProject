package com.idm.scim.ui;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.idm.scim.dto.User;
import com.idm.scim.service.IUserService;

@Named
@SessionScoped
public class AddUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5260401131386762129L;

	final static Logger logger = Logger.getLogger(AddUser.class);
	
	@Inject
	private User user;
	
	@Inject
	private IUserService userService;
	
	public String execute() {
		logger.info("Entering execute method");
		String returnValue = "fail";
		String name=user.toString();
		try {
			returnValue = getUserService().save(getUser());
			logger.info("Save Successfull" +name);
		} catch (Exception e) {
			logger.error("error while saving user: Message: "+e.getMessage());
		}
		return returnValue;
	}

	public User getUser() {
		return this.user;
	}

	
	public void setUser(User user) {
		this.user = user;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
