package com.takeoff.model;

public class RedemptionHistory implements RedemptionSummary{
	
public Long couponId;
	
	public Long customerId;
	
	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getRedemOn() {
		return redemOn;
	}

	public void setRedemOn(String redemOn) {
		this.redemOn = redemOn;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}

	public Long vendorId;
	
	public String redemOn;
	
	public String customerName;
	
	public String vendorName;
	
	public String couponType;
	
	public String address;
	
	public String contact;
	
	public String customerContact;
	
	public String category;
	
	public String subCategory;

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSubCategory() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
