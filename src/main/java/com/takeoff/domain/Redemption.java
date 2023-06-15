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
public class Redemption  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5922691580888304788L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "couponId")
	VendorCoupons coupon;

	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "vendorId")
	UserDetails vendor;
	
	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "customerId")
	UserDetails customer;
	
	String passcode;
	
	@Column(columnDefinition="datetime")
	Timestamp   validTill;
	
	
	@Column(columnDefinition="datetime")
	Timestamp   redemOn;
	
	
	public Timestamp getRedemOn() {
		return redemOn;
	}

	public void setRedemOn(Timestamp redemOn) {
		this.redemOn = redemOn;
	}
	
	
	Boolean vendorAccepted=false;
	
	Boolean userRedempted=false;
	
	public Boolean getVendorAccepted() {
		return vendorAccepted;
	}

	public void setVendorAccepted(Boolean vendorAccepted) {
		this.vendorAccepted = vendorAccepted;
	}

	public Boolean getUserRedempted() {
		return userRedempted;
	}

	public void setUserRedempted(Boolean userRedempted) {
		this.userRedempted = userRedempted;
	}

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VendorCoupons getCoupon() {
		return coupon;
	}

	public void setCoupon(VendorCoupons coupon) {
		this.coupon = coupon;
	}

	public UserDetails getVendor() {
		return vendor;
	}

	public void setVendor(UserDetails vendor) {
		this.vendor = vendor;
	}

	public UserDetails getCustomer() {
		return customer;
	}

	public void setCustomer(UserDetails customer) {
		this.customer = customer;
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}

	public Timestamp getValidTill() {
		return validTill;
	}

	public void setValidTill(Timestamp validTill) {
		this.validTill = validTill;
	}

	

	
	
}
