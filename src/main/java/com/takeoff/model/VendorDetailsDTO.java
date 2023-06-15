package com.takeoff.model;

import com.takeoff.domain.UserDetails;
import com.takeoff.domain.VendorDetails;

public class VendorDetailsDTO {

	
	public VendorDetailsDTO()
	{
		
	}
	
	public VendorDetailsDTO(VendorDetails vendorDetails)
	{
		this.vendorId=vendorDetails.getUser().getUserId();
		this.name=vendorDetails.getUser().getName();
		this.contact=vendorDetails.getUser().getContact();
		this.email=vendorDetails.getUser().getEmail();
		this.address=vendorDetails.getAddress();
		this.details=vendorDetails.getUser().getMessage();
		this.city=vendorDetails.getUser().getCity();
		this.logo="data:image/jpeg;base64,"+vendorDetails.getLogo();
		this.isDisabled=vendorDetails.getUser().getIsDisabled();
		this.loginId=vendorDetails.getUser().getLoginId();
	}
	
	public VendorDetailsDTO(UserDetails userDetails)
	{
		this.vendorId=userDetails.getUserId();
		this.name=userDetails.getName();
		this.contact=userDetails.getContact();
		this.email=userDetails.getEmail();
		this.loginId=userDetails.getLoginId();
		
		this.details=userDetails.getMessage();
		this.city=userDetails.getCity();
		this.isDisabled=userDetails.getIsDisabled();
		
	}
	



	Long vendorId;
	String name="";
	String contact="";
	String email="";
	String address="";
	String details="";
	String city="";
	String logo="";
	Long loginId;
	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}




	Boolean isDisabled=false;
	
	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	
}
