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
	VendorCoupons couponId;

	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "vendorId")
	UserDetails vendorId;
	
	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "customerId")
	UserDetails customerId;
	
	String passcode;
	
	@Column(columnDefinition="datetime")
	Timestamp   validTill;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VendorCoupons getCouponId() {
		return couponId;
	}

	public void setCouponId(VendorCoupons couponId) {
		this.couponId = couponId;
	}

	public UserDetails getVendorId() {
		return vendorId;
	}

	public void setVendorId(UserDetails vendorId) {
		this.vendorId = vendorId;
	}

	public UserDetails getCustomerId() {
		return customerId;
	}

	public void setCustomerId(UserDetails customerId) {
		this.customerId = customerId;
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
