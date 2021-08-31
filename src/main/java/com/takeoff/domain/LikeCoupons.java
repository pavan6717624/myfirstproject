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
	 
	 Boolean like;
	 
	 Boolean disLike;
	 
	 
	
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

	public Boolean getLike() {
		return like;
	}

	public void setLike(Boolean like) {
		this.like = like;
	}

	public Boolean getDisLike() {
		return disLike;
	}

	public void setDisLike(Boolean disLike) {
		this.disLike = disLike;
	}

	

}
