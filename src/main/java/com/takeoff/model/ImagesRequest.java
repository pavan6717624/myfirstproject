package com.takeoff.model;

import java.util.List;

public class ImagesRequest {
	
	Long vendorId = 0l;
	List<Long> imageIds=null;
	
	Long category = 0l;
	Long subCategory = 0l;
	String city = "";
	String keywords = "";
	
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
	
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	public List<Long> getImageIds() {
		return imageIds;
	}
	public void setImageIds(List<Long> imageIds) {
		this.imageIds = imageIds;
	}
	

}
