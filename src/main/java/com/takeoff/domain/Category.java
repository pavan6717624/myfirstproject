package com.takeoff.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category  implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5282912572139216945L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String categoryName;
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
