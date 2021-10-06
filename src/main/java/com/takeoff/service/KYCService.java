package com.takeoff.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.takeoff.domain.CustomerDetails;
import com.takeoff.domain.KYCDetails;
import com.takeoff.domain.Statement;
import com.takeoff.model.ImageStatusDTO;
import com.takeoff.model.KYCDetailsDTO;
import com.takeoff.repository.CustomerDetailsRepository;
import com.takeoff.repository.KYCDetailsRepository;
import com.takeoff.repository.StatementRepository;



@Service
public class KYCService {

	@Autowired
	KYCDetailsRepository kycRepository;
	
	@Autowired
	CustomerDetailsRepository customerDetailsRepository;
	
	@Autowired
	StatementRepository statementRepository;
	
	@Autowired
	CouponService couponService;
	
	public List<KYCDetailsDTO> getKYCDetails(String message) {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		System.out.println(Long.valueOf(userDetails.getUsername()));
		
		if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Admin")))
		{
		
			List<KYCDetailsDTO> details = kycRepository.getKYCDetails(0l,message);
			
			List<Long> customerIds=details.stream().map(d -> d.getCustomerId()).collect(Collectors.toList());
			
			List<KYCDetailsDTO> otherDetails = kycRepository.getOtherCustomerDetails(customerIds);
			
			details.addAll(otherDetails);
			
					
			return details;
		}
		else
		{
		
		List<KYCDetailsDTO> details = kycRepository.getKYCDetails(Long.valueOf(userDetails.getUsername()),message);
		
		if(details.size() == 0)
		{
		
			details = kycRepository.getWalletDetails(Long.valueOf(userDetails.getUsername()));
		}
				
		System.out.println(details.get(0).getWalletAmount());
		
		return details;
		}
	}
	@Transactional
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
		
		
		
		return getKYCDetails("Pan Updated Successfully").get(0);
	}
	@Transactional
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
		
		
		
		return getKYCDetails("KYC Updated Successfully").get(0);
	}
	@Transactional
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
			
			Statement statement=new Statement();
			statement.setAmount(addAmount);
			statement.setCustomer(cdetails);
			statement.setDate(details.getPanStatusOn());
			statement.setDescription("Pan Updated");
			
			statementRepository.save(statement);
			
			cdetails.setWalletAmount(amount+addAmount);
			customerDetailsRepository.save(cdetails);
		}
		
		return kycRepository.getKYCDetails(Long.valueOf(customerId),"Pan "+status+" for "+customerId).get(0);
	}
	@Transactional
	public KYCDetailsDTO verifyKycStatus(String customerId, String status) {
		System.out.println(customerId+" "+status+" in kyc Status");
		
		KYCDetails details = kycRepository.findByCustomerId(Long.valueOf(customerId));
		details.setKycStatus(status);
		details.setKycStatusOn(Timestamp.valueOf(LocalDateTime.now()));
		
		kycRepository.save(details);
		
		return kycRepository.getKYCDetails(Long.valueOf(customerId),"KYC "+status+" for "+customerId).get(0);
	}
	
	@Transactional
	public KYCDetailsDTO creditAmount(String customerId, String creditAmount, String creditDate) {
		
		CustomerDetails customer = customerDetailsRepository.findByUserId(Long.valueOf(customerId)).get();
		
		 if(Double.parseDouble(creditAmount) <=0)
		  {
		    		    
		    return kycRepository.getKYCDetails(Long.valueOf(customerId),"Please Provide Valid Credit Amount").get(0);
			
		    
		  }
		 else if(Double.parseDouble(creditAmount) > customer.getWalletAmount())
		  {
		 
		  return kycRepository.getKYCDetails(Long.valueOf(customerId),"Cannot Credit more than the Wallet Amount").get(0);
			
		  }
		
		Statement statement=new Statement();
		statement.setCustomer(customer);
		
		statement.setAmount(Double.parseDouble(creditAmount));
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				
		statement.setDate(Timestamp.valueOf(LocalDateTime.parse(creditDate,dateFormatter)));
		
		statement.setDescription("Amount credited to Bank Account");
		
		customer.setWalletAmount(customer.getWalletAmount()-Double.parseDouble(creditAmount));
		
		customer.setCreditAmount(customer.getCreditAmount()+Double.parseDouble(creditAmount));
		
		customerDetailsRepository.save(customer);
		
		statementRepository.save(statement);
				
		return kycRepository.getKYCDetails(Long.valueOf(customerId),"Amount ("+creditAmount+") Credited to "+customerId).get(0);
	}



}
