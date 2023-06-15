package com.takeoff.service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.takeoff.domain.CustomerDetails;
import com.takeoff.domain.Redemption;
import com.takeoff.domain.ScanCode;
import com.takeoff.domain.UserDetails;
import com.takeoff.domain.VendorCoupons;
import com.takeoff.domain.VendorDetails;
import com.takeoff.model.RedemptionDTO;
import com.takeoff.model.RedemptionSummary;
import com.takeoff.model.ResponseStatusDTO;
import com.takeoff.model.ScanCodeDTO;
import com.takeoff.repository.CustomerDetailsRepository;
import com.takeoff.repository.RedemptionRepository;
import com.takeoff.repository.ScanCodeRepository;
import com.takeoff.repository.UserDetailsRepository;
import com.takeoff.repository.VendorCouponsRepository;
import com.takeoff.repository.VendorDetailsRepository;

@Service
public class RedemptionService {
	
	@Autowired
	VendorCouponsRepository vendorCouponsRepository;
	@Autowired
	UserDetailsRepository userDetailsRepository;
	@Autowired
	RedemptionRepository redemptionRepository;
	
	@Autowired
	ScanCodeRepository scanCodeRepository;
	
	@Autowired
	UtilService utilService;
	
	@Autowired
	VendorDetailsRepository vendorDetailsRepository;
	
	@Autowired
	CustomerDetailsRepository customerRepository;
	
	
	public RedemptionDTO generateRedemption(RedemptionDTO redemptionDTO, int length,int code) {
		
		
		org.springframework.security.core.userdetails.UserDetails userDetails = (org.springframework.security.core.userdetails.UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Long userId=Long.valueOf(userDetails.getUsername());
		
		
		
		VendorCoupons coupon =  vendorCouponsRepository.findById(redemptionDTO.getCouponId()).get();
		
		CustomerDetails customerDetails = customerRepository.findByUserId(userId).get();
		
		Long freeSubscriptionsCount= customerRepository.getFreeSubscriptionsCount(customerDetails.getReferCode());
		Long premiumSubscriptionsCount= customerRepository.getPremiumSubscriptionsCount(customerDetails.getReferCode());		

				

		
		String refererCode = customerDetails.getRefererId();		
		
		String subscriptionType = userDetailsRepository.findById(userId).get().getType();	
			
		if(subscriptionType.equalsIgnoreCase("free"))	
		{	
			Long freeSubscriberRedemptionCount = vendorCouponsRepository.freeSubscriberRedemptionCount(userId);	
				
			System.out.println("Free subscription Count :: "+freeSubscriberRedemptionCount);	
				
			if(coupon.getCouponType().getId() == 1L)	
			{	
			redemptionDTO.setStatus(false);	
			redemptionDTO.setMessage("Sorry! Complimentary Coupon Redemptions are only for PAID Subscriptions");	
			return redemptionDTO;	
			}	
			else if(premiumSubscriptionsCount ==0l && freeSubscriberRedemptionCount >= ((freeSubscriptionsCount+1)*3)  && !Arrays.asList(UtilService.SPECIAL).contains(refererCode.toUpperCase()))	
			{	
				redemptionDTO.setStatus(false);	
				redemptionDTO.setMessage("Sorry! Your Redemption Limit("+((freeSubscriptionsCount+1)*3)+") is already reached for this Month.");	
				return redemptionDTO;	
			}	
			else if(premiumSubscriptionsCount ==0l && freeSubscriberRedemptionCount >= (10+(freeSubscriptionsCount*3)) && Arrays.asList(UtilService.SPECIAL).contains(refererCode.toUpperCase()))	
			{
				redemptionDTO.setStatus(false);	
				redemptionDTO.setMessage("Sorry! Your Redemption Limit("+(10+(freeSubscriptionsCount*3))+") is already reached for this Month.");	
				return redemptionDTO;	
			}
		}
		
		
		Long otherCount = vendorCouponsRepository.other3456Count(coupon.getVendor().getUser().getUserId(), userId);
	
		if(coupon.getCouponType().getId() == 1L)
		{
			Long complimentaryCount = vendorCouponsRepository.specific12Count( coupon.getVendor().getUser().getUserId(), userId);
			
			
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
	else if(coupon.getCouponType().getId() == 2L)
		{
			Long freeCount = vendorCouponsRepository.freeCount( coupon.getId(), userId);
			
			if(freeCount >= 1)
			{
				redemptionDTO.setStatus(false);
				redemptionDTO.setMessage("Sorry! You have already Redemed this Free Coupon. Free Coupons can be redemed Only Once.");
				return redemptionDTO;
			}
		
			
		}
	else if(coupon.getCouponType().getId() == 3L)
	{
		Long dailyDealsCount = vendorCouponsRepository.DailyDealsCount( coupon.getId(), userId);
		
		if(dailyDealsCount >= 1)
		{
			redemptionDTO.setStatus(false);
			redemptionDTO.setMessage("Sorry! You have already Redemed this Daily Deal / One Day Deal Coupon. Daily Deal / One Day Deal Coupons can be redemed Only Once Per Day.");
			return redemptionDTO;
		}
	
		
	}
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
		
		//if(code == 1)
			redemption.setVendorAccepted(true);
		
		redemptionRepository.save(redemption);
		
		redemptionDTO.setId(redemption.getId());
		redemptionDTO.setPasscode(passcode.substring(0,4));
		
		 SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss aa");  
		 
		String validTillStr = formatter.format(redemption.getValidTill());
		
		redemptionDTO.setValidTill(validTillStr);
		
String email= coupon.getVendor().getUser().getEmail();
UserDetails customer = userDetailsRepository.findById(userId).get();

		try
		{
		String mailText="\nYour Coupon is Ready for Redemption. Please proceed for Redemption\n"
				+ "\n\n------------------------------------------------------------\n"
				+ "\nShare Passcode to Customer for Redemption :: "+passcode.substring(4)
				+ "\nThis Passcode is valid till "+ validTillStr	
				+ "\n------------------------------------------------------------\n"
				+ "\n\nCoupon Id: "+coupon.getId()
				+ "\n\nCoupon Description: \n"+coupon.getHeader()+"\n"+coupon.getBody()+"\n"+coupon.getFooter()
				+ "\n\nCustomer Id: "+customer.getLoginId()
				+ "\n\nCustomer Name : "+customer.getName()
				+ "\n\nCustomer Contact :"+customer.getContact()
				+ "\n\nFor Concerns : Please contact Customer Care."
				+" \n\nThanks & Regards,"
				+ "\nTakeOff Team.";
		utilService.sendMessage(email,"Your Redemption Code for Coupon Id: "+coupon.getId()+", Customer Id: "+userId,mailText);
		}
		
		
		catch(Exception ex)
		{
			System.out.println("Could not Send Mail to Vendor on Redemption");
		}
		try
		{
		String smsText = "Id-"+coupon.getId()+""
				+ "\nDesc-"+coupon.getHeader()
				+ "\nContact-"+customer.getContact()
				+ "\nPasscode-"+passcode.substring(4)
				+ "\nValidTill-"+validTillStr;
		
		//utilService.sendSMS(coupon.getVendor().getUser().getContact(), smsText);
		
		}
		catch(Exception ex)
		{
			System.out.println("Could not Send SMS to Vendor on Redemption");
		}
	
		
		}
		else
		{
			redemptionDTO.setId(redemption.getId());
			redemptionDTO.setPasscode(redemption.getPasscode().substring(0,4));
			 SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss aa");  
			 
			 if(code == 1 && !redemption.getVendorAccepted())
			 {
				 redemption.setVendorAccepted(true);
				 redemptionRepository.save(redemption);
					
			 }
			 
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


public RedemptionDTO customerRedemption(RedemptionDTO redemptionDTO) {
	
	RedemptionDTO redemp = new RedemptionDTO();
	
	Boolean acceptRedemptionStatus = false;
	
	org.springframework.security.core.userdetails.UserDetails userDetails = (org.springframework.security.core.userdetails.UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	Long userId=Long.valueOf(userDetails.getUsername());

	UserDetails customer = userDetailsRepository.findById(userId).get();
	
	VendorCoupons coupon =  vendorCouponsRepository.findById(redemptionDTO.getCouponId()).get();
	
	System.out.println("customerRedemption "+ redemptionDTO.getCouponId()+" "+redemptionDTO.getCustomerId()+" "+coupon.getVendor().getUser().getUserId());
	
	Redemption redemption = redemptionRepository.findVendorAcceptedPasscode(redemptionDTO.getCouponId(),redemptionDTO.getCustomerId(),coupon.getVendor().getUser().getUserId(), Timestamp.valueOf(LocalDateTime.now()));
	
	if(redemption!=null)
	{
		if(redemption.getPasscode().substring(8-redemptionDTO.getPasscode().length()).equals(redemptionDTO.getPasscode()))
		{
			redemption.setUserRedempted(true);
			redemption.setRedemOn(Timestamp.valueOf(LocalDateTime.now()));
			redemptionRepository.save(redemption);
			acceptRedemptionStatus= true;
		}
	}
	
	redemp.setStatus(acceptRedemptionStatus);
	redemp.setCouponId(redemptionDTO.getCouponId());
	 SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss aa");  
	 
		String redemOn = formatter.format(redemption.getRedemOn());
	redemp.setRedemOn(redemOn);
	String email= coupon.getVendor().getUser().getEmail();
	
	try
	{
	
	String mailText="\nYour Coupon has been Redemmed Successfully on "+ redemOn +".\n"
			
			+ "\n\nCoupon Id: "+coupon.getId()
			+ "\n\nCoupon Description: \n"+coupon.getHeader()+"\n"+coupon.getBody()+"\n"+coupon.getFooter()
			+ "\n\nCustomer Id: "+customer.getLoginId()
			+ "\n\nCustomer Name : "+customer.getName()
			+ "\n\nCustomer Contact :"+customer.getContact()
			+ "\n\nFor Concerns : Please contact Customer Care."
			+" \n\nThanks & Regards,"
			+ "\nTakeOff Team.";
	utilService.sendMessage(email,"Your Coupon (Id: "+coupon.getId()+") has been Successfully Redemmed by Customer Id: "+userId,mailText);
	}
	
	
	catch(Exception ex)
	{
		System.out.println("Could not Send Succes Mail to Vendor on Redemption");
	}
	
	return redemp;
}


public RedemptionDTO sendRedemptionCode(String scanCode, String couponId) {
	
	RedemptionDTO redemp = new RedemptionDTO();
	try
	{
	VendorCoupons coupon = vendorCouponsRepository.findById(Long.valueOf(couponId)).get();
	
	Long vendorId = coupon.getVendor().getVendorid();
	
	System.out.println(scanCode+" "+vendorId);
	
	Optional<ScanCode> checkScanCode= scanCodeRepository.findByCodeAndVendorId(scanCode,vendorId);
	
	
	if(checkScanCode.isPresent())
	{
		
org.springframework.security.core.userdetails.UserDetails userDetails = (org.springframework.security.core.userdetails.UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Long userId=Long.valueOf(userDetails.getUsername());
		redemp.setCouponId(Long.valueOf(couponId));
		redemp.setCustomerId(userId);
		redemp.setVendorId(vendorId);
		redemp = generateRedemption(redemp, 8, 1);
		
		

		
			redemp.setPasscode("");
	}
	else
		{
		
		redemp.setStatus(false);
		redemp.setMessage("Sorry! Coupon and Vendor Mapping is Incorrect.");
		}
	}
	catch(Exception ex)
	{	
		System.out.println(ex);
		redemp.setStatus(false);
		redemp.setMessage("Sorry! Coupon and Vendor Mapping is Incorrect.");
		
	}
	return redemp;
	

}

public List<RedemptionSummary> getRedemptionSummary()
{
	org.springframework.security.core.userdetails.UserDetails userDetails = (org.springframework.security.core.userdetails.UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	Long userId=Long.valueOf(userDetails.getUsername());
	
	Long customerId=0l,vendorId=0l;
	
	if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Vendor")))
			{
			vendorId=userId;
			}
	else if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Customer")))
			{
		customerId=userId;
			}
	else if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Admin")))
	{
		customerId=0l;
		vendorId=0l;
	}
	else
		return null;
	
	
	return redemptionRepository.getRedemptionSummary(customerId,vendorId);
}


public List<ScanCodeDTO> getCodes()
{
	return scanCodeRepository.getCodes();
}


public RedemptionDTO updateScanCode(String scanCode) {
	
	RedemptionDTO status=new RedemptionDTO();
	
	org.springframework.security.core.userdetails.UserDetails userDetails = (org.springframework.security.core.userdetails.UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	Long userId=Long.valueOf(userDetails.getUsername());
	
	VendorDetails vendor = vendorDetailsRepository.findByUserId(userId).get();
	
	Optional<ScanCode> check = scanCodeRepository.findByCodeAndVendorId(scanCode , vendor.getVendorid());
	
	if(check.isPresent())
	{
		status.setStatus(false);
		status.setMessage("Scan Code already Exists. Please Try New Scan Code.");
		return status;
	}
	
	ScanCode code=new ScanCode();
	code.setCode(scanCode);
	code.setVendor(vendor);
	
	scanCodeRepository.save(code);
	
	if(code.getId() != null)
		{
		status.setStatus(true);
		status.setMessage("Scan Code Updated Successfully.");
		return status;
		}
	else
	{
		status.setStatus(false);
		status.setMessage("Scan Code Updation Failed. Try Again.");
		return status;
		}
}

}
