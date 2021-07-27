package com.takeoff.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.domain.CustomerDetails;
import com.takeoff.domain.CustomerMapping;
import com.takeoff.model.StatusDTO;
import com.takeoff.model.SubscriptionDTO;
import com.takeoff.repository.CustomerDetailsRepository;
import com.takeoff.repository.CustomerMappingRepository;

@Service
public class CustomerService {
	
	@Autowired
	CustomerDetailsRepository customerDetailsRepository;
	@Autowired
	CustomerMappingRepository customerMappingRepository;
	public Boolean checkRefererId(String refercode)
	{
		Optional<CustomerDetails> customerDetails = customerDetailsRepository.findByReferCode(refercode);
		if(customerDetails.isPresent())
			return true;
		else
			return false;
		
	}
	
	public StatusDTO createCustomer(SubscriptionDTO subscription)
	{
		CustomerDetails customer=new CustomerDetails(subscription);
		customer.setMappingStatus(false);
		customer.setPaymentStatus(true);
		customer.setMessage("Payment Successful");
		customerDetailsRepository.save(customer);
		
		int rows = customerDetailsRepository.customerMapping(customer.getCustomerId(), 
				customerDetailsRepository.findByReferCode(customer.getRefererId()).get().getCustomerId());
		
		if(rows == 1)
		{
		Long customerId = customer.getCustomerId();
		customer.setReferCode("TO"+customerId);
		customer.setMappingStatus(true);
		customer.setMessage("Mapping Successful");
		customerDetailsRepository.save(customer);
		
		
		Long parentId = customerMappingRepository.getParentIdForCustomerId(customer.getCustomerId());
		
		CustomerDetails parent=customerDetailsRepository.findByCustomerId(parentId).get();
		
		parent.setWalletAmount(parent.getWalletAmount()+500);
		customerDetailsRepository.save(parent);
		}
		
		StatusDTO statusDto=new StatusDTO(customer);
		return statusDto;
		
	}

}
