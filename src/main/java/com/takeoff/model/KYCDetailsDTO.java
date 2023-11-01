package com.takeoff.model;

public interface KYCDetailsDTO {
	
	public Long getId() ;
	
	public Long getCustomerId();
	
	public String getName();
	
	public Long getContact();
	


	public String getPan() ;

	public String getCname() ;
	public String getBname();
	
	public String getAccount();
	
	public String getEmail();


	public String getIfsc() ;
	
	public Double getWalletAmount();
	
	public Double getCreditAmount();
	
	public String getPanStatus();
	
	public String getKycStatus();

	public String getStatement();
	
	public String getMessage();
}
