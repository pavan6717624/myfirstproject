package com.takeoff.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.takeoff.domain.ResetPassword;
import com.takeoff.domain.UserDetails;
import com.takeoff.repository.ResetPasswordRepository;
import com.takeoff.repository.UserDetailsRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class UtilService {
	
	  @Autowired
	    private JavaMailSender javaMailSender;
	  
	  @Autowired
	   private UserDetailsRepository userDetailsRepository;
	  
	  @Autowired
	   private RedemptionService redemptionService;
	  
	  @Autowired
	  private ResetPasswordRepository resetPasswordRepository;
	  
	  
	  @Autowired
	  private CustomerService customerService;
	  
	  
	  private final static String ACCOUNT_SID = "ACf482a1d636d1f7a1948262ea473b868c";
	   private final static String AUTH_ID =  System.getenv("TwilioKey");
	   
	   
	   static {
		      Twilio.init(ACCOUNT_SID, AUTH_ID);
		   }
		   
	   
	   public final static String SPECIAL[]= {"TO10149","TO104034","TO10202","TO106635","TO107179"};
	
	public String generatePassword(int length) throws NoSuchAlgorithmException {
	      String capitalCaseLetters = "ABCDEFGHJKLMNPQRSTUVWXYZ";
	      String lowerCaseLetters = "ABCDEFGHJKLMNPQRSTUVWXYZ";
	      String specialCharacters = "ABCDEFGHJKLMNPQRSTUVWXYZ";
	      String numbers = "123456789";
	      String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
	      SecureRandom random = new SecureRandom();
	      char[] password = new char[length];

	      password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
	      password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
	      password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
	      password[3] = numbers.charAt(random.nextInt(numbers.length()));
	   
	      for(int i = 4; i< length ; i++) {
	         password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
	      }
	      return new String(password);
	   }
	
	
	  public Boolean scheduleInterview(String id, String date, String time,String duration, String email1, String email2)
		    {
		    	try
		    	{
		    		
		    		System.out.println("Your Interview got Scheduled with Interview Name"+(Integer.parseInt(id)+1)+" on " + date+" "+time +" for " + duration +" hours.");
		    		
		    		System.out.println("Your Interview got Scheduled on " + date+" "+time +" for " + duration +" hours with Student - "+email1);
		    		
		    		sendMessage(email1, "Interview Scheduled", "Your Interview got Scheduled with Interview Name"+(Integer.parseInt(id)+1)+" on " + date+" "+time +" for " + duration +" hours.");
		    		
		    		sendMessage(email2, "Interview Scheduled", "Your Interview got Scheduled on " + date+" "+time +" for " + duration +" hours with Student - "+email1);
		    		return true;
		    	}
		    	catch(Exception ex)
		    	{
		    	return false;	
		    	}
		    }
	
		 
		   public void sendSMS(String smsTo, String text){
			   
			   try
			   {
		      Message.creator(new PhoneNumber("+91"+smsTo), new PhoneNumber("+15128438283"),
		         text).create();
			   }
			   catch(Exception ex)
			   {
				   try
				   {
				   Message.creator(new PhoneNumber("+91"+smsTo), new PhoneNumber("+15128438283"),
					         text).create();
				   }
				   catch(Exception ex1)
				   {
					   System.out.println("Error in Sending SMS for "+smsTo+ex);   
				   }
				
			   }
		   }
		   
		   public void sendMessage(String mailTo, String subject, String text)
		   {
			   SimpleMailMessage msg = new SimpleMailMessage();
		     msg.setTo(mailTo);
			msg.setFrom("support@thetakeoff.in");
		     msg.setSubject(subject);
		     msg.setText(text);
			   try
			   {
			  
			     javaMailSender.send(msg);
			   }
			   catch(Exception ex)
			   {
				   try
				   {
				   javaMailSender.send(msg);
				   }
				   catch(Exception ex1)
				   {
					   System.out.println("Error in Sending Mail for "+mailTo + "\n"+ex);   
				   }
				
			   }
		   }
		   
		   
		   public String getSHA(String input) throws NoSuchAlgorithmException
		    { 
		        // Static getInstance method is called with hashing SHA 
		        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
		  
		        // digest() method called 
		        // to calculate message digest of an input 
		        // and return array of byte
		        return toHexString(md.digest(input.getBytes(StandardCharsets.UTF_8))); 
		    }
		    
		    public String toHexString(byte[] hash)
		    {
		        // Convert byte array into signum representation 
		        BigInteger number = new BigInteger(1, hash); 
		  
		        // Convert message digest into hex value 
		        StringBuilder hexString = new StringBuilder(number.toString(16)); 
		  
		        // Pad with leading zeros
		        while (hexString.length() < 64) 
		        { 
		            hexString.insert(0, '0'); 
		        } 
		  
		        return hexString.toString(); 
		    }

			public Boolean generateMailPasscode(String userId, String email, String city) 
			{
				
				try
				{
					
					
					
					
//					List<com.takeoff.domain.UserDetails> mobile = userDetailsRepository.findByContactNumber(userId);
//		
//		if(mobile.size() == 1)
//			userId=mobile.get(0).getLoginId()+"";
//		else if(mobile.size() > 1)
//		{
//			
//			return false;
//		}
//		
//		
//		if(userId.startsWith("TO"))
//			
//            userId=userId.substring(2,7);
//		
//		userId=userId.substring(0,5);
//					
//					userId=userId.toLowerCase().replaceAll("to","");
//					
//					Boolean isUser = customerService.isUser(userId);
//					
//					if(!isUser)
//					{
//						
//						
//						return false;
//						
//						
//					}
//								
//					userId=userId.substring(0,5);
//					
//					
				Optional<UserDetails> user = userDetailsRepository.checkForPasswordChange(userId,email,city);
					
				if(!user.isPresent())
				{
					return false;
				}
				
				System.out.println(user.get().getEmail() +" "+user.get().getCity()+" "+email+" "+city);
				
				if(user.isPresent() && user.get().getEmail().trim().toLowerCase().equals(email.trim().toLowerCase()) && user.get().getCity().trim().toLowerCase().equals(city.trim().toLowerCase()))
				{
					
					String otp = resetPasswordRepository.getPasscode(Long.valueOf(userId), Timestamp.valueOf(LocalDateTime.now()))+"";
					
					
					if(otp.equals("null"))		
					{
						otp = redemptionService.generatePasscode(6);
						ResetPassword reset= new ResetPassword();
						reset.setCustomer(user.get());
						reset.setPasscode(Long.valueOf(otp));
						reset.setValidTill(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)));
						resetPasswordRepository.save(reset);
						
					}
			sendMessage(email, "TAKE OFF - Password Change OTP", "Your Password change OTP is " + otp);
			
			return true;
				}
				}
				catch(Exception ex)
				{
					System.out.println("sending reset passcode :: "+ex);
				}
			
				
				return false;
			}





			public Boolean checkPasswordOTP(String userId, String otp) {
				
				String dbotp = resetPasswordRepository.getPasscode(Long.valueOf(userId), Timestamp.valueOf(LocalDateTime.now()))+"";
				
				return dbotp.equals(otp);
				
			
			}
	

}
