package com.takeoff.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CustomerDetails {

	@Id
	@GeneratedValue
	Long customerId;
	
	
	Long refererId;
	String referCode;
	
	String name;
	Long contact;
	
	String email;
	String password;
	
	Boolean paymentStatus;
	Boolean mappingStatus;
	
	Double walletAmount;
	
	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getRefererId() {
		return refererId;
	}

	public void setRefererId(Long refererId) {
		this.refererId = refererId;
	}

	public String getReferCode() {
		return referCode;
	}

	public void setReferCode(String referCode) {
		this.referCode = referCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getContact() {
		return contact;
	}

	public void setContact(Long contact) {
		this.contact = contact;
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

	public Boolean getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Boolean getMappingStatus() {
		return mappingStatus;
	}

	public void setMappingStatus(Boolean mappingStatus) {
		this.mappingStatus = mappingStatus;
	}

	public Double getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(Double walletAmount) {
		this.walletAmount = walletAmount;
	}

	
}


