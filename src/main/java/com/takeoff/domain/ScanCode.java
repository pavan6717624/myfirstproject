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
public class ScanCode  implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4212001438797794351L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public VendorDetails getVendor() {
		return vendor;
	}

	public void setVendor(VendorDetails vendor) {
		this.vendor = vendor;
	}

	String code;
	
	@OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "vendorId")
	VendorDetails vendor;
	

}
