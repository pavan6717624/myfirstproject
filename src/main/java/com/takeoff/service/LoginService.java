package com.takeoff.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.domain.CustomerDetails;
import com.takeoff.repository.CustomerDetailsRepository;

@Service
public class LoginService {
	
	@Autowired
	CustomerDetailsRepository customerDetailsRepository;

	public Boolean login(String userid,String password) {
		Optional<CustomerDetails> customer = customerDetailsRepository.findByCustomerIdAndPassword(Long.valueOf(userid), password);
		return customer.isPresent();
	}

}
