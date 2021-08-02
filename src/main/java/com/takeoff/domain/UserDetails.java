package com.takeoff.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.takeoff.model.SubscriptionDTO;

@Entity
public class UserDetails implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3956721357336114735L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long userId;
	String password="";
	String name="";
	String contact="";
	String email="";
	String city="";
	String message="";
	String userType="";
	
	public UserDetails()
	{
		
	}
	
	public UserDetails(SubscriptionDTO subscription)
	{
		this. password=subscription.getPassword();
		this.  name=subscription.getName();
		this.  contact=subscription.getContact();
		this.  email=subscription.getEmail();
		this.  city=subscription.getCity();
		this.  message=subscription.getMessage();
		this.  userType="Customer";
	}
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

}
