package com.takeoff.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class LikeCoupons implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8895310441481839906L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "user_id")
	UserDetails customer;
	 
	 @OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "coupon_id")
	 VendorCoupons coupon;
	 
		Boolean likeCoupon;
		 
		 Boolean disLikeCoupon;
		 
	 
	 public Boolean getLikeCoupon() {
		return likeCoupon;
	}

	public void setLikeCoupon(Boolean likeCoupon) {
		this.likeCoupon = likeCoupon;
	}

	public Boolean getDisLikeCoupon() {
		return disLikeCoupon;
	}

	public void setDisLikeCoupon(Boolean disLikeCoupon) {
		this.disLikeCoupon = disLikeCoupon;
	}


	 
	
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

	public VendorCoupons getCoupon() {
		return coupon;
	}

	public void setCoupon(VendorCoupons coupon) {
		this.coupon = coupon;
	}

	

}
