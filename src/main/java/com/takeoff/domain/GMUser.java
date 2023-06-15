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
@Table(name = "gm_users")
@Data
public class GMUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4532661528913824133L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String password = "";
	String name = "";
	Long contact = 0l;
	String email = "";
	String city = "";
	String message = "";

	Long loginId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleId")
	GMRole role;
	
	
}
