package com.takeoff.model;

import java.util.List;

public class ImagesRequest {
	
	Long vendorId = 0l;
	List<Long> imageIds=null;
	
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
