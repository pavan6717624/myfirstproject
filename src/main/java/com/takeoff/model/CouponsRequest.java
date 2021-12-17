package com.takeoff.model;

import java.util.List;

public class CouponsRequest {
	
	Long userId = 0l;
	List<Long> couponIds=null;
	Long category = 0l;
	Long subCategory = 0l;
	String city = "";
	String keywords = "";
	Long vendorSelected = 0l;
	
	public Long getVendorSelected() {
		return vendorSelected;
	}
	public void setVendorSelected(Long vendorSelected) {
		this.vendorSelected = vendorSelected;
	}
	public Long getCategory() {
		return category;
	}
	public void setCategory(Long category) {
		this.category = category;
	}
	public Long getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(Long subCategory) {
		this.subCategory = subCategory;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	
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
