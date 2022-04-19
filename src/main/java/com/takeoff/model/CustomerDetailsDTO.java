package com.takeoff.model;

public interface CustomerDetailsDTO {
	
	public Long getUserId() ;

	public String getName() ;

	public String getContact() ;

	public String getEmail();

	public String getCity() ;
	
		public String getType();


	public String getProfession() ;

	public String getGender() ;

	public String getRazorpay_payment_id() ;
	public String getRazorpay_order_id() ;
	

	public String getRefererId() ;

	public String getReferCode() ;

	public Boolean getPaymentStatus() ;
	public Boolean getMappingStatus() ;

	public Double getWalletAmount() ;

	public String getJoinDate();


}
