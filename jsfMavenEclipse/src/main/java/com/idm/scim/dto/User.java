package com.idm.scim.dto;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unboundid.scim2.common.BaseScimResource;

@Named
@SessionScoped
public class User extends BaseScimResource implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -559110332013525442L;
	//private String name;
	private String firstName;
	private String lastName;
	private String userName;
	private Date dob;
	private String email;
	@JsonIgnore
	private String password;
	@JsonIgnore
	private String confirmPassword;
	private String role;
	private String address;
	private String mobile;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*
	 * public User(String name, String firstName, String lastName, Date dob, String
	 * email, String password, String confirmPassword, String role, String address,
	 * String mobile) { super(); this.name = name; this.firstName = firstName;
	 * this.lastName = lastName; this.dob = dob; this.email = email; this.password =
	 * password; this.confirmPassword = confirmPassword; this.role = role;
	 * this.address = address; this.mobile = mobile; }
	 * 
	 */
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/*
	 * public String getName() { return name; }
	 * 
	 * public void setName(String name) { this.name = name; }
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return firstName + " " + lastName + " " + email;
	}
}
