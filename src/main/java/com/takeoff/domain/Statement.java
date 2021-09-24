package com.takeoff.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Statement implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -367860616831689274L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "userId")
	UserDetails user;
	
	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "childId")
	UserDetails child;
	
	@Column(columnDefinition="datetime")
	 Timestamp  date;
	
	Double amount;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDetails getUser() {
		return user;
	}

	public void setUser(UserDetails user) {
		this.user = user;
	}

	public UserDetails getChild() {
		return child;
	}

	public void setChild(UserDetails child) {
		this.child = child;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	

}
