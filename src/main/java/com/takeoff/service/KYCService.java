package com.takeoff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.takeoff.model.KYCDetailsDTO;
import com.takeoff.repository.KYCDetailsRepository;

@Service
public class KYCService {

	@Autowired
	KYCDetailsRepository kycRepository;
	public List<KYCDetailsDTO> getKYCDetails() {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return kycRepository.getKYCDetails(Long.valueOf(userDetails.getUsername()));
	}

}
