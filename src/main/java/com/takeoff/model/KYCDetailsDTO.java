package com.takeoff.model;

public interface KYCDetailsDTO {
	
	public Long getId() ;
	
	public Long getCustomerId();
	
	public String getName();
	
	public Long getContact();


	public String getPan() ;

	public String getCname() ;
	public String getBname();


	public String getIfsc() ;
	
	public Double walletAmount();

}
