package com.takeoff.model;

import com.takeoff.domain.CustomerDetails;

public class StatusDTO {
	
	
	Boolean status;
	String orderId;
	String paymentId;
	Long customerId;
	String message;
	String referCode;
	Long loginId;

	public Long getLoginId() {
		return loginId;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public StatusDTO() {
		
	}
	public StatusDTO(SubscriptionDTO subscrbe) {
		
		this.status = false;
		this.orderId = subscrbe.getRazorpay_order_id();
		this.paymentId=subscrbe.getRazorpay_payment_id();
		this.message=" Payment Failed. If Amount Debited, it will be refunded in 5 Business Days.";
	}
	
	public StatusDTO(CustomerDetails customer) {
		
		this.status = customer.getMappingStatus() && customer.getPaymentStatus();
		this.orderId = customer.getRazorpay_order_id();
		this.paymentId=customer.getRazorpay_payment_id();
		this.customerId=customer.getUser().getUserId();
		this.referCode=customer.getReferCode();
		this.message = customer.getUser().getMessage();
		this.loginId=customer.getUser().getLoginId();
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getReferCode() {
		return referCode;
	}
	public void setReferCode(String referCode) {
		this.referCode = referCode;
	}
	
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	

}
