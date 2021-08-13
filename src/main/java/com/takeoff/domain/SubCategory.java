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
public class SubCategory implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1426391913288992703L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@OneToOne
	 @JoinColumn(name = "category_id")
	Category category;
	
	String subCategoryName;
	
	Boolean subCategoryVisibility=true;
	
	Boolean isDeleted=false;	
	

	Boolean mandatoryComplimentary = true;
	
	
	public Boolean getMandatoryComplimentary() {
		return mandatoryComplimentary;
	}

	public void setMandatoryComplimentary(Boolean mandatoryComplimentary) {
		this.mandatoryComplimentary = mandatoryComplimentary;
	}
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getSubCategoryVisibility() {
		return subCategoryVisibility;
	}

	public void setSubCategoryVisibility(Boolean subCategoryVisibility) {
		this.subCategoryVisibility = subCategoryVisibility;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	
	
	
}
