package com.takeoff.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.domain.CustomerDetails;
import com.takeoff.repository.CustomerDetailsRepository;

@Service
public class CustomerService {
	
	@Autowired
	CustomerDetailsRepository customerDetailsRepository;
	public Boolean checkRefererId(String refercode)
	{
		Optional<CustomerDetails> customerDetails = customerDetailsRepository.findByReferCode(refercode);
		if(customerDetails.isPresent())
			return true;
		else
			return false;
		
	}

}
