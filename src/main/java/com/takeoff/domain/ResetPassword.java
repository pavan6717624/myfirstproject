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
public class ResetPassword implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 923329208433725023L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "customerId")
	UserDetails customer;
	
	Long passcode;
	
	@Column(columnDefinition="datetime")
	Timestamp   validTill;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDetails getCustomer() {
		return customer;
	}

	public void setCustomer(UserDetails customer) {
		this.customer = customer;
	}

	public Long getPasscode() {
		return passcode;
	}

	public void setPasscode(Long passcode) {
		this.passcode = passcode;
	}

	public Timestamp getValidTill() {
		return validTill;
	}

	public void setValidTill(Timestamp validTill) {
		this.validTill = validTill;
	}

	

}
