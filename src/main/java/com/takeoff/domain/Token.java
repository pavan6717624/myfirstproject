package com.takeoff.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Token implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -762320434005612867L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	String token;
	

}
