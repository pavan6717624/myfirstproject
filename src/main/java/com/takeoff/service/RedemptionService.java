package com.takeoff.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class RedemptionService {
	
	
	
	public String generateRedemption(int length) {
		
		
		return generatePasscode(length);
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
