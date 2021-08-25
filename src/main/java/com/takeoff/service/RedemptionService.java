package com.takeoff.service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.domain.Redemption;
import com.takeoff.domain.UserDetails;
import com.takeoff.domain.VendorCoupons;
import com.takeoff.model.RedemptionDTO;
import com.takeoff.repository.RedemptionRepository;
import com.takeoff.repository.UserDetailsRepository;
import com.takeoff.repository.VendorCouponsRepository;

@Service
public class RedemptionService {
	
	@Autowired
	VendorCouponsRepository vendorCouponsRepository;
	@Autowired
	UserDetailsRepository userDetailsRepository;
	@Autowired
	RedemptionRepository redemptionRepository;
	
	
	public RedemptionDTO generateRedemption(RedemptionDTO redemptionDTO, int length) {
		
		
		
		
		VendorCoupons coupon =  vendorCouponsRepository.findById(redemptionDTO.getCouponId()).get();
		
		System.out.println(redemptionDTO.getCouponId()+" "+redemptionDTO.getCustomerId()+" "+coupon.getVendor().getUser().getUserId());
		
		Redemption redemption = redemptionRepository.findPasscode(redemptionDTO.getCouponId(),redemptionDTO.getCustomerId(),coupon.getVendor().getUser().getUserId(), Timestamp.valueOf(LocalDateTime.now()));
		
		if(redemption == null)
		{
		redemption=new Redemption();
		redemption.setCoupon(coupon);
		redemption.setCustomer(userDetailsRepository.findById(redemptionDTO.getCustomerId()).get());
		redemption.setVendor(coupon.getVendor().getUser());
		
		String passcode=generatePasscode(length);
		redemption.setPasscode(passcode);
		
		
		
		Timestamp validTill = Timestamp.valueOf(LocalDateTime.now().plusMinutes(15));
		
		redemption.setValidTill(validTill);
		
		
		redemptionRepository.save(redemption);
		
		redemptionDTO.setId(redemption.getId());
		redemptionDTO.setPasscode(passcode.substring(0,4));
		
		 SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss aa");  
		 
		String validTillStr = formatter.format(redemption.getValidTill());
		
		redemptionDTO.setValidTill(validTillStr);
		
		}
		else
		{
			redemptionDTO.setId(redemption.getId());
			redemptionDTO.setPasscode(redemption.getPasscode().substring(0,4));
			 SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss aa");  
			 
			 String validTill = formatter.format(redemption.getValidTill());
				
				redemptionDTO.setValidTill(validTill);
		}
	
		
		return redemptionDTO;
	}
	
	
public RedemptionDTO vendorRedemptionProcess(RedemptionDTO redemptionDTO) {
		
		
		
		
		VendorCoupons coupon =  vendorCouponsRepository.findById(redemptionDTO.getCouponId()).get();
		
		System.out.println(redemptionDTO.getCouponId()+" "+redemptionDTO.getCustomerId()+" "+coupon.getVendor().getUser().getUserId());
		
		Redemption redemption = redemptionRepository.findPasscode(redemptionDTO.getCouponId(),redemptionDTO.getCustomerId(),coupon.getVendor().getUser().getUserId(), Timestamp.valueOf(LocalDateTime.now()));
		
		if(redemption != null)
		
		{
			redemptionDTO.setId(redemption.getId());
			redemptionDTO.setPasscode(redemption.getPasscode().substring(0,4));
			 SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss aa");  
			 
			 String validTill = formatter.format(redemption.getValidTill());
				
				redemptionDTO.setValidTill(validTill);
		}
	
		
		return redemptionDTO;
	}
	
	public String generatePasscode(int length) {
	      String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	      String numbers = "1234567890";
	      String combinedChars = capitalCaseLetters + numbers;
	      SecureRandom random = new SecureRandom();
	      char[] password = new char[length];
	      password[0] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
	     
	      password[1] = numbers.charAt(random.nextInt(numbers.length()));
	   
	      for(int i = 2; i< length ; i++) {
	         password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
	      }
	      return new String(password);
	   }

}
