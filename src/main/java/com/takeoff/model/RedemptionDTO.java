package com.takeoff.model;

public class RedemptionDTO {

	

	Long id;
	

	Long couponId;

	
	Long vendorId;
	
	
	Long customerId;
	
	String passcode;
	
	
	String   validTill;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getCouponId() {
		return couponId;
	}


	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}


	public Long getVendorId() {
		return vendorId;
	}


	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}


	public Long getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}


	public String getPasscode() {
		return passcode;
	}


	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}


	public String getValidTill() {
		return validTill;
	}


	public void setValidTill(String validTill) {
		this.validTill = validTill;
	}


	
}
