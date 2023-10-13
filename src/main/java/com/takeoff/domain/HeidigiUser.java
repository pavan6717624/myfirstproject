package com.takeoff.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Data
@Table(name = "hd_users")
public class HeidigiUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6871647410790643083L;
	/**
	 * 
	 */
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String password = "";
	String name = "";
	Long mobile = 0l;
	String email = "";
	
	String message = "";

	

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleId")
	HeidigiRole role;
	

}
