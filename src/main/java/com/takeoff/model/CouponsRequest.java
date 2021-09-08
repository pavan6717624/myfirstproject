package com.takeoff.model;

import java.util.List;

public class CouponsRequest {
	
	Long userId = 0l;
	List<Long> couponIds=null;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<Long> getCouponIds() {
		return couponIds;
	}
	public void setCouponIds(List<Long> couponIds) {
		this.couponIds = couponIds;
	}
	
	

}
