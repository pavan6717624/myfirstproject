package com.takeoff.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class LikeCouponsId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8708857417796978693L;
	
	private Long userId;
    private Long couponId;
    
	
	 public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}


}
