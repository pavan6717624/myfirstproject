package com.takeoff.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class CustomerMapping{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long mappingId;
	
	 @OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "customerId")
	CustomerDetails customer;
	Long refererId;
	Long parentId;

}
