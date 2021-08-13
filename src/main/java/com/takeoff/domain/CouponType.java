package com.takeoff.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CouponType implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4670847693904566126L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String couponType;
	Boolean couponTypeVisibility=true;
	Boolean isDeleted=false;
	
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Boolean getCouponTypeVisibility() {
		return couponTypeVisibility;
	}
	public void setCouponTypeVisibility(Boolean couponTypeVisibility) {
		this.couponTypeVisibility = couponTypeVisibility;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCouponType() {
		return couponType;
	}
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	
}
