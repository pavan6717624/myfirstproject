package com.takeoff.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.takeoff.domain.CustomerDetails;
import com.takeoff.domain.KYCDetails;
import com.takeoff.model.ImageStatusDTO;
import com.takeoff.model.KYCDetailsDTO;
import com.takeoff.repository.CustomerDetailsRepository;
import com.takeoff.repository.KYCDetailsRepository;

@Service
public class KYCService {

	@Autowired
	KYCDetailsRepository kycRepository;
	
	@Autowired
	CustomerDetailsRepository customerDetailsRepository;
	
	@Autowired
	CouponService couponService;
	
	public List<KYCDetailsDTO> getKYCDetails() {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		System.out.println(Long.valueOf(userDetails.getUsername()));
		
		if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Admin")))
		{
		
			List<KYCDetailsDTO> details = kycRepository.getKYCDetails(0l);
			
			List<Long> customerIds=details.stream().map(d -> d.getCustomerId()).collect(Collectors.toList());
			
			List<KYCDetailsDTO> otherDetails = kycRepository.getOtherCustomerDetails(customerIds);
			
			details.addAll(otherDetails);
			
					
			return details;
		}
		else
		{
		
		List<KYCDetailsDTO> details = kycRepository.getKYCDetails(Long.valueOf(userDetails.getUsername()));
		
		if(details.size() == 0)
		{
		
			details = kycRepository.getWalletDetails(Long.valueOf(userDetails.getUsername()));
		}
				
		System.out.println(details.get(0).getWalletAmount());
		
		return details;
		}
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
	
	public KYCDetailsDTO updateKyc(MultipartFile file,String cname,String bname,String account,String ifsc) {
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
		
		
		ImageStatusDTO statement = couponService.getImage(file);
		
		details.setStatement(statement.getImage());
		
		
		
		
		kycRepository.save(details);
		
		
		
		return getKYCDetails().get(0);
	}
	public KYCDetailsDTO verifyPanStatus(String customerId, String status) {
		
		System.out.println(customerId+" "+status+" in Pan Status");
		
		KYCDetails details = kycRepository.findByCustomerId(Long.valueOf(customerId));
		details.setPanStatus(status);
		details.setPanStatusOn(Timestamp.valueOf(LocalDateTime.now()));
		kycRepository.save(details);
		
		if(status.equals("Approved"))
		{
			CustomerDetails cdetails = customerDetailsRepository.findByUserId(Long.valueOf(customerId)).get();
			Double amount = cdetails.getWalletAmount();
			Double addAmount = ((amount)/400)*75;
			
			cdetails.setWalletAmount(amount+addAmount);
			customerDetailsRepository.save(cdetails);
		}
		
		return kycRepository.getKYCDetails(Long.valueOf(customerId)).get(0);
	}
	public KYCDetailsDTO verifyKycStatus(String customerId, String status) {
		System.out.println(customerId+" "+status+" in kyc Status");
		
		KYCDetails details = kycRepository.findByCustomerId(Long.valueOf(customerId));
		details.setKycStatus(status);
		details.setKycStatusOn(Timestamp.valueOf(LocalDateTime.now()));
		
		kycRepository.save(details);
		
		return kycRepository.getKYCDetails(Long.valueOf(customerId)).get(0);
	}



}
