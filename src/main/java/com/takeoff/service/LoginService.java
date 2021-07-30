package com.takeoff.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.domain.CustomerDetails;
import com.takeoff.domain.VendorDetails;
import com.takeoff.model.LoginStatusDTO;
import com.takeoff.repository.CustomerDetailsRepository;
import com.takeoff.repository.VendorDetailsRepository;

@Service
public class LoginService {
	
	@Autowired
	CustomerDetailsRepository customerDetailsRepository;
	
	@Autowired
	VendorDetailsRepository vendorDetailsRepository;

	public LoginStatusDTO login(String userid,String password) {
		
		Long customerId=0l;
		String vendorId=userid;
		
		LoginStatusDTO loginStatus=new LoginStatusDTO();
		
		
		
		Boolean status=false;
		try
		{
			customerId = Long.valueOf(userid);
			Optional<CustomerDetails> customer = customerDetailsRepository.findByCustomerIdAndPassword(customerId, password);
			 status =  customer.isPresent();
			 loginStatus.setUserId(userid);
				loginStatus.setUserType("Customer");
				loginStatus.setLoginStatus(status);
			
			
		}
		catch( NumberFormatException exception)
		{
			Optional<VendorDetails> vendor = vendorDetailsRepository.findByVendorIdAndPassword(vendorId, password);
			 status =  vendor.isPresent();
			 loginStatus.setUserId(userid);
				loginStatus.setUserType("Vendor");
				loginStatus.setLoginStatus(status);
		}
		
		
		return loginStatus;
	}

}
