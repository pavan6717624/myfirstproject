package com.takeoff.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ImageDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1137373385198496953L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(length = 100000)
	String image;
	String keywords = "";
	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "userId")
	UserDetails user;
	
	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "subCategoryId")
	SubCategory subCateogry;
	
	Boolean deleted=false;
	Long deletedBy=0l;
	
	public Long getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(Long deletedBy) {
		this.deletedBy = deletedBy;
	}

	
	
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public SubCategory getSubCateogry() {
		return subCateogry;
	}

	public void setSubCateogry(SubCategory subCateogry) {
		this.subCateogry = subCateogry;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}


	
	
	public UserDetails getUser() {
		return user;
	}

	public void setUser(UserDetails user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
