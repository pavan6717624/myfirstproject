package com.takeoff.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.razorpay.RazorpayException;
import com.takeoff.domain.CustomerDetails;
import com.takeoff.domain.KYCDetails;
import com.takeoff.domain.Statement;
import com.takeoff.domain.UserDetails;
import com.takeoff.model.CustomerDetailsDTO;
import com.takeoff.model.GstDetails;
import com.takeoff.model.NotificationDTO;
import com.takeoff.model.StatsDTO;
import com.takeoff.model.StatusDTO;
import com.takeoff.model.SubscriptionDTO;
import com.takeoff.model.TdsDTO;
import com.takeoff.repository.CustomerDetailsRepository;
import com.takeoff.repository.CustomerMappingRepository;
import com.takeoff.repository.KYCDetailsRepository;
import com.takeoff.repository.RolesRepository;
import com.takeoff.repository.StatementRepository;
import com.takeoff.repository.UserDetailsRepository;
import com.takeoff.repository.VendorCouponsRepository;

@Service
public class CustomerService {
	
	@Autowired
	CustomerDetailsRepository customerDetailsRepository;
	@Autowired
	CustomerMappingRepository customerMappingRepository;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	UtilService utilService;
	
	@Autowired
	RazorpayService razorpayService;
	
	
	@Autowired
	LoginService loginService;
	
	
	@Autowired
	DisplayService displayService;
	
	
	@Autowired
	CouponService couponService;
	
	@Autowired
	CouponTypeService couponTypeService;
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CustomerDetailsRepository customerRepository;
	
	
	@Autowired
	RolesRepository rolesRepository;
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	KYCDetailsRepository kycRepository;
	
	@Autowired
	StatementRepository statementRepository;
	
	@Autowired
	VendorCouponsRepository vendorCouponsRepository;
	
	@Autowired
	UserDetailsRepository userRepository;

	
	public List<TdsDTO> getTDS(String fromDate, String toDate)
	{
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return customerMappingRepository.getTDS(Timestamp.valueOf(LocalDateTime.parse(fromDate,dateFormatter)),Timestamp.valueOf(LocalDateTime.parse(toDate,dateFormatter)));
	}
	
	public Boolean checkRefererId(String refercode)
	{
		System.out.println("referral Code :: "+refercode);
		Optional<CustomerDetails> customerDetails = customerDetailsRepository.findByReferCode(refercode);
		if(customerDetails.isPresent())
			return true;
		else
			return false;
		
	}
	
	public Boolean isUser(String username)
	{
	  Optional<UserDetails> user = userDetailsRepository.isUser(Long.valueOf(username));
	  return user.isPresent();
	}
	
	
	
	public StatusDTO subscribe(SubscriptionDTO subscription)
	{
		subscription.setRole(rolesRepository.findByRoleName("Customer").get());
		StatusDTO statusDto = new StatusDTO();
		System.out.println("in subscribe");
		
		try
		{
		Boolean paymentStatus =razorpayService.subscribe(subscription);	
		System.out.println("in subscribe " +paymentStatus);
		
		if(paymentStatus)
		{	
			String password=subscription.getPassword();
			subscription.setPassword(utilService.getSHA(password));
			statusDto = customerService.createCustomer(subscription);
		 if(!statusDto.getStatus())
		 {
			 statusDto.setCustomerId(0L);
			 statusDto.setReferCode("0");
			  statusDto.setLoginId(0L);
			 statusDto.setMessage(" Mapping Failed. If Amount Debited, it will be refunded in 5 Business Days. ");
		 }
		 try
		 {
		 String text="\nCongrats! Your Account got Created in TakeOff\n"
		     		+ "Login Id / Referral Code: "+statusDto.getReferCode()+"\n"
		     		+ "Password: "+password+"\n"
		     		+ "Login & Enjoy the TakeOff @ www.thetakeoff.in";
		 utilService.sendMessage(subscription.getEmail(), "Your TakeOff Account", text);
		 utilService.sendSMS(subscription.getContact(), text);
		 
		 }
		 catch( Exception ex)
		 {
			 System.out.println("Issue in sending message and sms "+ex);
		 }
		}
		else
		{
			System.out.println("in subscribe false " +paymentStatus);
			statusDto=new StatusDTO(subscription);
		}
		}
		catch(RazorpayException | IOException | NoSuchAlgorithmException ex)
		{
			
			statusDto=new StatusDTO(subscription);
			statusDto.setMessage(" User Creation Failed. If Amount Debited, it will be refunded in 5 Business Days. ");
		}
		
		return statusDto;
	}
	
	public StatusDTO checkCustomerDetails(String mobileNumber, String email)
	{
		StatusDTO status=new StatusDTO();
		
		
		 
		List<UserDetails> login=userRepository.findByContactNumber(mobileNumber);
		if(login.size() > 0)
		{
			status.setStatus(false);
			status.setMessage("Provided Mobile Number already Exists.\n");
		}
		
		login=userRepository.findByEmail(email);
		if(login.size() > 0)
		{
			status.setStatus(false);
			status.setMessage(status.getMessage()+"Provided Email already Exists.");
		}
		
		return status;
		
	}

			
	public StatusDTO subscribeFree(SubscriptionDTO subscription)	
	{	
		subscription.setRole(rolesRepository.findByRoleName("Customer").get());	
		StatusDTO statusDto = new StatusDTO();	
		System.out.println("in subscribe");	
			
		try	
		{	
		Boolean paymentStatus = true;		
		System.out.println("in subscribe " +paymentStatus);	
			
		if(paymentStatus)	
		{		
			String password=utilService.generatePassword(8);	
			subscription.setPassword(utilService.getSHA(password));	
			statusDto = customerService.createCustomer(subscription);	
		 if(!statusDto.getStatus())	
		 {	
			 statusDto.setCustomerId(0L);	
			 statusDto.setReferCode("0");	
			  statusDto.setLoginId(0L);	
			 statusDto.setMessage(" Mapping Failed. Please contact Customer Care.");	
		 }	
		 try	
		 {	
		 String text="Your FREE Account got Created in TakeOff\n"	
		     		+ "Login Id / Referral Code: "+statusDto.getReferCode()+"\n"	
		     		+ "Password: "+password+"\n"	
		     		+ "Only 3 Redemptions per Month\n"	
		     		+ "Login to www.thetakeoff.in";	
		 utilService.sendMessage(subscription.getEmail(), "Your TakeOff FREE Account", text);	
		 utilService.sendSMS(subscription.getContact(), text);	
		 	
		 }	
		 catch( Exception ex)	
		 {	
			 System.out.println("Issue in sending message and sms "+ex);	
		 }	
		}	
		else	
		{	
			System.out.println("in subscribe false " +paymentStatus);	
			statusDto=new StatusDTO(subscription);	
		}	
		}	
		catch( NoSuchAlgorithmException ex)	
		{	
				
			statusDto=new StatusDTO(subscription);	
			statusDto.setMessage(" User Creation Failed. Please contact Customer Care.");	
		}	
			
		return statusDto;	
	}	
	
	
	@Transactional
	public StatusDTO createCustomer(SubscriptionDTO subscription)
	{
		
		UserDetails user=new UserDetails(subscription);
		user.setJoinDate(Timestamp.valueOf(LocalDateTime.now()));
		user = userDetailsRepository.save(user);
		
		SecureRandom random = new SecureRandom();
		
		 int randomInt = random.nextInt(10);
		user.setLoginId(Long.valueOf(user.getUserId()+""+randomInt));
		
		userDetailsRepository.save(user);
		
		
		UserDetails executive = null;
		
		if(subscription.getExecutiveId()!=null && subscription.getExecutiveId().length() > 0)
		executive = userDetailsRepository.findByExecutiveId(Long.valueOf(subscription.getExecutiveId())).get();
		
		
		CustomerDetails customer=new CustomerDetails(subscription,user,executive);
		customer.setMappingStatus(false);
		customer.setPaymentStatus(true);
		customer.getUser().setMessage("Payment Successful");
		customerDetailsRepository.save(customer);
		
		int rows = customerDetailsRepository.customerMapping(customer.getUser().getUserId(), 
				customerDetailsRepository.findByReferCode(customer.getRefererId()).get().getUser().getUserId(),subscription.getSubscription());
		
		if(rows == 1)
		{
		customer.setReferCode("TO"+customer.getUser().getLoginId());
		customer.setMappingStatus(true);
		customer.getUser().setMessage("Mapping Successful");
		customer.setWalletAmount(0d);
		customer.setCreditAmount(0d);
		customerDetailsRepository.save(customer);
		
		
		Long parentId = customerMappingRepository.getParentIdForUserId(customer.getUser().getUserId());
		
		CustomerDetails parent=customerDetailsRepository.findByUserId(parentId).get();
		
		KYCDetails details = kycRepository.findByCustomerId(parentId);
		
			
			
		if(subscription.getSubscription().equalsIgnoreCase("Pay"))	
		{	
			
			Double addAmount = 240d; //addAmount = 400d;	
		if(details != null && details.getPanStatus().equals("Approved"))	
			addAmount = 285d; //addAmount = 475d;	
			
			
		parent.setWalletAmount(parent.getWalletAmount()+ addAmount);	
		customerDetailsRepository.save(parent);	
			
			
			
		Statement statement=new Statement();	
		statement.setAmount(addAmount);	
		statement.setCustomer(parent);	
		statement.setDate(user.getJoinDate());	
		statement.setDescription("Referral Reward for referring "+user.getUserId());	
			
		statementRepository.save(statement);	
		}	
			
		Statement statement=new Statement();	
		statement=new Statement();	
		statement.setAmount(0d);	
		statement.setCustomer(customer);	
		statement.setDate(user.getJoinDate());	
		if(subscription.getSubscription().equalsIgnoreCase("Pay"))	
		statement.setDescription("Subscribed to TakeOff EXCLUSIVE Account");	
		else	
		statement.setDescription("Subscribed to TakeOff FREE Account");	
			
		statementRepository.save(statement);
		
		
		
		
		}
		
		StatusDTO statusDto=new StatusDTO(customer);
		return statusDto;
		
	}




	public CustomerDetailsDTO getCustomerAccountDetails(Long userId) {
		
		return customerDetailsRepository.getCustomerAccountDetails(userId);
		
		
		
		
	}




	public List<GstDetails> gstDetails() {
		
		
		return customerDetailsRepository.gstDetails();
	}

	public Boolean forgetPassword(String userId, String newpassword) {

			
		


		Optional<UserDetails> user=userDetailsRepository.findById(Long.valueOf(userId));
	
		
		Boolean status=false;
		
		
		
		
			user.get().setPassword(newpassword);
			userDetailsRepository.save(user.get());
			if(user.get().getUserId()!=null)
				status=true;
	
		
		return status;
	}

	public List<CustomerDetailsDTO> getAllCustomerAccountDetails() {
		
		return customerDetailsRepository.getAllCustomerAccountDetails();
	}
	
public List<CustomerDetailsDTO> getInvestorCustomerAccountDetails() {
		
	org.springframework.security.core.userdetails.UserDetails userDetails = (org.springframework.security.core.userdetails.UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
		return customerDetailsRepository.getInvestorCustomerAccountDetails(Long.valueOf(userDetails.getUsername()));
	}
	
	public List<StatsDTO> getUserStats() {
		
		return customerDetailsRepository.getUserStats();
	}
	
	public Long getWalletBalance() {
		
		return customerDetailsRepository.getWalletBalance();
	}

	public List<CustomerDetailsDTO> getExecutiveCustomerAccountDetails() {
		org.springframework.security.core.userdetails.UserDetails userDetails = (org.springframework.security.core.userdetails.UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return customerDetailsRepository.getExecutiveCustomerAccountDetails(Long.valueOf(userDetails.getUsername()));
	
	}

	public NotificationDTO getNotification() {
		
		org.springframework.security.core.userdetails.UserDetails userDetails = (org.springframework.security.core.userdetails.UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Long userId=Long.valueOf(userDetails.getUsername());
		Long freeSubscriberRedemptionCount = vendorCouponsRepository.freeSubscriberRedemptionCount(userId);	
		
		NotificationDTO notification=new NotificationDTO();
		
		String subscriptionType = userDetailsRepository.findById(userId).get().getType();	
		
CustomerDetails customerDetails = customerRepository.findByUserId(userId).get();
		
		String refererCode = customerDetails.getRefererId();		
		
		if(subscriptionType.equals("Free") && !Arrays.asList(UtilService.SPECIAL).contains(refererCode.toUpperCase()))
		{
			
			notification.setType("");
			notification.setHeader("Welcome to Customer!");	
			notification.setMessage("Your Redeemed Coupons for the month:: "+freeSubscriberRedemptionCount+"\nYour Available Redemptions for the Month :: "+(3-freeSubscriberRedemptionCount));	
			
			
		}	
		else if(subscriptionType.equals("Free") && Arrays.asList(UtilService.SPECIAL).contains(refererCode.toUpperCase()))
		{
			notification.setType("Special");
			notification.setHeader("Welcome to Special Customer!");	
			notification.setMessage("Your Redeemed Coupons for the month:: "+freeSubscriberRedemptionCount+"\nYour Available Redemptions for the Month :: "+(10-freeSubscriberRedemptionCount));	
			
		}
		else
		{
			notification.setType("Premium");
			notification.setHeader("Welcome to Premium Customer!");	
			notification.setMessage("Your are eligible for Unlimited Redemptions");	
			
		}
		
		return notification;	
		
	}

}
