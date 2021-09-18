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
import com.takeoff.model.ResponseStatusDTO;
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
		Long otherCount = vendorCouponsRepository.other3456Count(coupon.getVendor().getUser().getUserId());
	
		if(coupon.getCouponType().getId() == 1L)
		{
			Long complimentaryCount = vendorCouponsRepository.specific12Count( coupon.getVendor().getUser().getUserId());
			
			
			if(complimentaryCount >= 1)
			{
				redemptionDTO.setStatus(false);
				redemptionDTO.setMessage("Sorry! You cannot Redeem this Coupon as You have already redemmed the Complimentary Coupon from this Vendor.");
				return redemptionDTO;
			}
			else if(complimentaryCount == 0 && otherCount == 0)
			{
				redemptionDTO.setStatus(false);
				redemptionDTO.setMessage("Sorry! You can ONLY redeem Complimentary Coupons IF ONE of Redeemable, Discount, Limit, Daily Deals got Redemption from the SAME OUTLET.");
				return redemptionDTO;
			}
				
		}
//		else if(coupon.getCouponType().getId() == 2L)
//		{
//			Long freeCount = vendorCouponsRepository.specific12CountFree();
//			
//			if(freeCount >= otherCount)
//			{
//				redemptionDTO.setStatus(false);
//				redemptionDTO.setMessage("Sorry! You can redeem ONE Free Coupons ONLY IF ONE of Redeemable, Discount, Limit, Daily Deals got Redemption from the OTHER (than this) OUTLET.");
//				return redemptionDTO;
//			}
//			else if(freeCount < otherCount && coupon.)
//			
//		}
		
		System.out.println(redemptionDTO.getCouponId()+" "+redemptionDTO.getCustomerId()+" "+coupon.getVendor().getUser().getUserId()+" "+LocalDateTime.now());
		
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
	
		redemptionDTO.setStatus(true);
		redemptionDTO.setMessage("Eligible for Redemption");
		return redemptionDTO;
	}
	
	
public RedemptionDTO vendorRedemptionProcess(RedemptionDTO redemptionDTO) {
		
		
		
		
		VendorCoupons coupon =  vendorCouponsRepository.findById(redemptionDTO.getCouponId()).get();
		
		System.out.println(redemptionDTO.getCouponId()+" "+redemptionDTO.getCustomerId()+" "+coupon.getVendor().getUser().getUserId());
		
		Redemption redemption = redemptionRepository.findPasscode(redemptionDTO.getCouponId(),redemptionDTO.getCustomerId(),coupon.getVendor().getUser().getUserId(), Timestamp.valueOf(LocalDateTime.now()));
		
		if(redemption != null)
		
		{
			redemptionDTO.setId(redemption.getId());
			redemptionDTO.setPasscode(redemption.getPasscode().substring(4,8));
			 SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss aa");  
			 
			 String validTill = formatter.format(redemption.getValidTill());
				
				redemptionDTO.setValidTill(validTill);
		}
	
		
		return redemptionDTO;
	}
	
	public String generatePasscode(int length) {
	    //  String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	      String numbers = "1234567890";
	     // String combinedChars = capitalCaseLetters + numbers;
	      
	      String combinedChars = numbers;
	      
	      SecureRandom random = new SecureRandom();
	      char[] password = new char[length];
	    //  password[0] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
	     
	   //   password[1] = numbers.charAt(random.nextInt(numbers.length()));
	   
	      for(int i = 0; i< length ; i++) {
	         password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
	      }
	      return new String(password);
	   }


public Boolean acceptRedemption(RedemptionDTO redemptionDTO) {
		
		Boolean acceptRedemptionStatus = false;
		
		VendorCoupons coupon =  vendorCouponsRepository.findById(redemptionDTO.getCouponId()).get();
		
		System.out.println(redemptionDTO.getCouponId()+" "+redemptionDTO.getCustomerId()+" "+coupon.getVendor().getUser().getUserId());
		
		Redemption redemption = redemptionRepository.findPasscode(redemptionDTO.getCouponId(),redemptionDTO.getCustomerId(),coupon.getVendor().getUser().getUserId(), Timestamp.valueOf(LocalDateTime.now()));
		
		if(redemption!=null)
		{
			if(redemption.getPasscode().equals(redemptionDTO.getPasscode()))
			{
				redemption.setVendorAccepted(true);
				redemptionRepository.save(redemption);
				acceptRedemptionStatus= true;
			}
		}
		
		return acceptRedemptionStatus;
	}

public ResponseStatusDTO acceptRedemptionWhatsApp(Long couponId, Long customerId, String passcode, String whatsappNumber) {
	
	ResponseStatusDTO status=new ResponseStatusDTO();
	
	status.setStatus(false);
	status.setMessage("");
	
	
	VendorCoupons coupon =  vendorCouponsRepository.findById(couponId).get();
	
	System.out.println(couponId+" "+customerId+" "+coupon.getVendor().getUser().getUserId());
	
	String contact="91"+coupon.getVendor().getUser().getContact();
	
	if(!contact.equals(whatsappNumber))
	{
		status.setMessage("Request received from UnMapped Number. Please contact Support Team");
		return status;
	}
	
	Redemption redemption = redemptionRepository.findPasscode(couponId,customerId,coupon.getVendor().getUser().getUserId(), Timestamp.valueOf(LocalDateTime.now()));
	
	if(redemption!=null)
	{
		if(redemption.getPasscode().substring(0,4).equals(passcode))
		{
			redemption.setVendorAccepted(true);
			redemptionRepository.save(redemption);
			status.setMessage("Share Passcode to Customer : " + redemption.getPasscode().substring(4,8));
			status.setStatus(true);
		}
		else
		{
			status.setMessage("Invalid Passcode..");
		}
	}
	else
		status.setMessage("No Requests found from Customer OR Passcode Expired.");
	
	return status;
}


public Boolean customerRedemption(RedemptionDTO redemptionDTO) {
	Boolean acceptRedemptionStatus = false;
	
	VendorCoupons coupon =  vendorCouponsRepository.findById(redemptionDTO.getCouponId()).get();
	
	System.out.println("customerRedemption "+ redemptionDTO.getCouponId()+" "+redemptionDTO.getCustomerId()+" "+coupon.getVendor().getUser().getUserId());
	
	Redemption redemption = redemptionRepository.findVendorAcceptedPasscode(redemptionDTO.getCouponId(),redemptionDTO.getCustomerId(),coupon.getVendor().getUser().getUserId(), Timestamp.valueOf(LocalDateTime.now()));
	
	if(redemption!=null)
	{
		if(redemption.getPasscode().equals(redemptionDTO.getPasscode()))
		{
			redemption.setUserRedempted(true);
			redemptionRepository.save(redemption);
			acceptRedemptionStatus= true;
		}
	}
	
	return acceptRedemptionStatus;
}




}
