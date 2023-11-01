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
public class KYCDetails implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7699616791070872141L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "customerId")
	CustomerDetails customer;
	
	String pan="";
	
	String cname="";
	
	String bname="";
	
	String ifsc="";
	
	String account = "";
	
	String panStatus="";
	
	String kycStatus="";
	
	@Column(columnDefinition="datetime")
	 Timestamp  panStatusOn;
	
	@Column(columnDefinition="datetime")
	 Timestamp  kycStatusOn;
	
	@Column(length = 100000)
	String statement;
	
	
	
	
	
	
	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public Timestamp getKycStatusOn() {
		return kycStatusOn;
	}

	public void setKycStatusOn(Timestamp kycStatusOn) {
		this.kycStatusOn = kycStatusOn;
	}

	public Timestamp getPanStatusOn() {
		return panStatusOn;
	}

	public void setPanStatusOn(Timestamp panStatusOn) {
		this.panStatusOn = panStatusOn;
	}


	
	

	
	
	
	
	public String getPanStatus() {
		return panStatus;
	}

	public void setPanStatus(String panStatus) {
		this.panStatus = panStatus;
	}

	public String getKycStatus() {
		return kycStatus;
	}

	public void setKycStatus(String kycStatus) {
		this.kycStatus = kycStatus;
	}

	
	
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomerDetails getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDetails customer) {
		this.customer = customer;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	
}
