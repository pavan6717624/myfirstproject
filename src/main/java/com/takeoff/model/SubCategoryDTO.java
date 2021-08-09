package com.takeoff.model;

public interface SubCategoryDTO {

	Long getCategoryId();
	String getCategoryName();
	Long getSubCategoryId();
	String getSubCategoryName();
	Boolean getCategoryVisibility();
	Boolean getSubCategoryVisibility();
	Boolean getIsCategoryDeleted();
	Boolean getIsSubCategoryDeleted();
	Boolean getMandatoryComplimentary();
}
