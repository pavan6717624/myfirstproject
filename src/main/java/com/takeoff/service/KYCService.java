package com.takeoff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.takeoff.domain.KYCDetails;
import com.takeoff.model.KYCDetailsDTO;
import com.takeoff.repository.CustomerDetailsRepository;
import com.takeoff.repository.KYCDetailsRepository;

@Service
public class KYCService {

	@Autowired
	KYCDetailsRepository kycRepository;
	
	@Autowired
	CustomerDetailsRepository customerDetailsRepository;
	
	public List<KYCDetailsDTO> getKYCDetails() {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		System.out.println(Long.valueOf(userDetails.getUsername()));
		
		return kycRepository.getKYCDetails(Long.valueOf(userDetails.getUsername()));
	}
	public KYCDetailsDTO updatePan(String pan) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		KYCDetails details = kycRepository.findByCustomerId(Long.valueOf(userDetails.getUsername()));
		
		if(details == null)
		{
			details=new KYCDetails();
			details.setCustomer(customerDetailsRepository.findByUserId(Long.valueOf(userDetails.getUsername())).get());
			
		}
		
		details.setPan(pan);
		details.setPanStatus("Submitted");
		kycRepository.save(details);
		
		
		
		return getKYCDetails().get(0);
	}
	
	public KYCDetailsDTO updateKyc(String cname,String bname,String account,String ifsc) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		KYCDetails details = kycRepository.findByCustomerId(Long.valueOf(userDetails.getUsername()));
		
		if(details == null)
		{
			details=new KYCDetails();
			details.setCustomer(customerDetailsRepository.findByUserId(Long.valueOf(userDetails.getUsername())).get());
			
		}
		
		details.setCname(cname);
		details.setBname(bname);
		details.setAccount(account);
		details.setIfsc(ifsc);
		details.setKycStatus("Submitted");
		kycRepository.save(details);
		
		
		
		return getKYCDetails().get(0);
	}


}
