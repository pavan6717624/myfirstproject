package com.takeoff.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.domain.CustomerDetails;
import com.takeoff.domain.UserDetails;
import com.takeoff.model.StatusDTO;
import com.takeoff.model.SubscriptionDTO;
import com.takeoff.repository.CustomerDetailsRepository;
import com.takeoff.repository.CustomerMappingRepository;
import com.takeoff.repository.UserDetailsRepository;

@Service
public class CustomerService {
	
	@Autowired
	CustomerDetailsRepository customerDetailsRepository;
	@Autowired
	CustomerMappingRepository customerMappingRepository;
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	public Boolean checkRefererId(String refercode)
	{
		Optional<CustomerDetails> customerDetails = customerDetailsRepository.findByReferCode(refercode);
		if(customerDetails.isPresent())
			return true;
		else
			return false;
		
	}
	@Transactional
	public StatusDTO createCustomer(SubscriptionDTO subscription)
	{
		
		UserDetails user=new UserDetails(subscription);
		
		user = userDetailsRepository.save(user);
		
		
		CustomerDetails customer=new CustomerDetails(subscription,user);
		customer.setMappingStatus(false);
		customer.setPaymentStatus(true);
		customer.getUser().setMessage("Payment Successful");
		customerDetailsRepository.save(customer);
		
		int rows = customerDetailsRepository.customerMapping(customer.getUser().getUserId(), 
				customerDetailsRepository.findByReferCode(customer.getRefererId()).get().getUser().getUserId());
		
		if(rows == 1)
		{
		Long customerId = customer.getUser().getUserId();
		customer.setReferCode("TO"+customerId);
		customer.setMappingStatus(true);
		customer.getUser().setMessage("Mapping Successful");
		customerDetailsRepository.save(customer);
		
		
		Long parentId = customerMappingRepository.getParentIdForUserId(customer.getUser().getUserId());
		
		CustomerDetails parent=customerDetailsRepository.findByUserId(parentId).get();
		
		parent.setWalletAmount(parent.getWalletAmount()+500);
		customerDetailsRepository.save(parent);
		}
		
		StatusDTO statusDto=new StatusDTO(customer);
		return statusDto;
		
	}

}
