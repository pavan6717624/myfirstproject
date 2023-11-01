package com.takeoff.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.takeoff.model.SubscriptionDTO;

@Entity
public class CustomerDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6555219717640930459L;


	public CustomerDetails()
	{
	
	}
	
	public CustomerDetails(SubscriptionDTO subscription, UserDetails user,UserDetails executive)
	{
		
		this.profession=subscription.getProfession();
		this.gender=subscription.getGender();
		this.user=user;
	
		this.razorpay_payment_id=subscription.getRazorpay_payment_id();
		this.razorpay_order_id=subscription.getRazorpay_order_id();
		this.razorpay_signature=subscription.getRazorpay_signature();
		
		
			this.executive = executive;
		
		this.refererId=subscription.getRefererid();
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long customerId;
	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "userId")
	UserDetails user;
	
	String profession="";
	String gender="";
	
	String razorpay_payment_id="";
	String razorpay_order_id="";
	String razorpay_signature="";
	
	
	String refererId="";
	String referCode="";
	

	
	Boolean paymentStatus=false;
	Boolean mappingStatus=false;
	
	Double walletAmount=0d;
	
	
	public UserDetails getExecutive() {
		return executive;
	}

	public void setExecutive(UserDetails executive) {
		this.executive = executive;
	}
	Double creditAmount = 0d;
	
	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "executiveId")
	UserDetails executive;
	


	public Double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public UserDetails getUser() {
		return user;
	}

	public void setUser(UserDetails user) {
		this.user = user;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRazorpay_payment_id() {
		return razorpay_payment_id;
	}

	public void setRazorpay_payment_id(String razorpay_payment_id) {
		this.razorpay_payment_id = razorpay_payment_id;
	}

	public String getRazorpay_order_id() {
		return razorpay_order_id;
	}

	public void setRazorpay_order_id(String razorpay_order_id) {
		this.razorpay_order_id = razorpay_order_id;
	}

	public String getRazorpay_signature() {
		return razorpay_signature;
	}

	public void setRazorpay_signature(String razorpay_signature) {
		this.razorpay_signature = razorpay_signature;
	}

	
	public String getRefererId() {
		return refererId;
	}

	public void setRefererId(String refererId) {
		this.refererId = refererId;
	}

	public String getReferCode() {
		return referCode;
	}

	public void setReferCode(String referCode) {
		this.referCode = referCode;
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


