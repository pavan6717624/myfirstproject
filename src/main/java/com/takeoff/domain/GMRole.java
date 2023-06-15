package com.takeoff.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "gm_roles")
public class GMRole implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3593220063988675351L;
	/**
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String roleName="";
	String displayName="";
	
	
	
	
	
	
	
}
