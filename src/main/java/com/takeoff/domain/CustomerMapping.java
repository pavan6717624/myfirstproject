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
public class CustomerMapping implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5658610914797452087L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long mappingId;
	
	 public Long getMappingId() {
		return mappingId;
	}
	public void setMappingId(Long mappingId) {
		this.mappingId = mappingId;
	}
	public UserDetails getCustomer() {
		return customer;
	}
	public void setCustomer(UserDetails customer) {
		this.customer = customer;
	}
	public Long getRefererId() {
		return refererId;
	}
	public void setRefererId(Long refererId) {
		this.refererId = refererId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "user_id")
	UserDetails customer;
	Long refererId;
	Long parentId;
	String type;

}
