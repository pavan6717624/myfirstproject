package com.takeoff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.domain.VendorDetails;
import com.takeoff.model.VendorDetailsDTO;
import com.takeoff.repository.VendorDetailsRepository;

@Service
public class VendorService {
	
	@Autowired
	VendorDetailsRepository vendorDetailsRepository;
	
	public VendorDetailsDTO getVendorDetails(Long vendorId)
	{
		return new VendorDetailsDTO(vendorDetailsRepository.findByUserId(vendorId).get());
	}
	
	public VendorDetails getVendorDetails(String vendorId)
	{
		return vendorDetailsRepository.findByUserId(Long.valueOf(vendorId)).get();
	}

	public VendorDetailsDTO getDesignerDetails(Long vendorId)
	{
		return new VendorDetailsDTO(vendorDetailsRepository.findByDesignerId(vendorId).get());
	}


}
