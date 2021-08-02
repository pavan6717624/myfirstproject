package com.takeoff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.model.ImageStatusDTO;
import com.takeoff.model.VendorDetailsDTO;
import com.takeoff.repository.VendorDetailsRepository;

@Service
public class VendorService {
	
	@Autowired
	VendorDetailsRepository vendorDetailsRepository;
	
	public VendorDetailsDTO getVendorService(Long vendorId)
	{
		return new VendorDetailsDTO(vendorDetailsRepository.findByUserId(vendorId).get());
	}




}
