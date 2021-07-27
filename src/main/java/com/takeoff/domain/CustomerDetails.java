package com.takeoff.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.takeoff.model.SubscriptionDTO;

@Entity
public class CustomerDetails {
	
	public CustomerDetails()
	{
	
	}
	
	public CustomerDetails(SubscriptionDTO subscription)
	{
		this.city=subscription.getCity();
		this.password=subscription.getPassword();

		this.name=subscription.getName();
		this.contact=subscription.getContact();
		this.email=subscription.getEmail();
		this.profession=subscription.getProfession();
		this.gender=subscription.getGender();
	
		this.razorpay_payment_id=subscription.getRazorpay_payment_id();
		this.razorpay_order_id=subscription.getRazorpay_order_id();
		this.razorpay_signature=subscription.getRazorpay_signature();
		this.message=subscription.getMessage();
		
		this.refererId=subscription.getRefererid();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long customerId;
	String password="";

	String name="";
	String contact="";
	String email="";
	String profession="";
	String gender="";
	String city="";;
	String razorpay_payment_id="";
	String razorpay_order_id="";
	String razorpay_signature="";
	String message="";
	
	String refererId="";
	String referCode="";
	

	
	Boolean paymentStatus=false;
	Boolean mappingStatus=false;
	
	Double walletAmount=0d;
	
	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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


