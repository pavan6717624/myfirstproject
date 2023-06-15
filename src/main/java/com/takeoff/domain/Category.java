package com.takeoff.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Category  implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5282912572139216945L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String categoryName;
	Boolean categoryVisibility=true;
	Boolean isDeleted = false;	
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public Boolean getCategoryVisibility() {
		return categoryVisibility;
	}

	public void setCategoryVisibility(Boolean categoryVisibility) {
		this.categoryVisibility = categoryVisibility;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
	
}
