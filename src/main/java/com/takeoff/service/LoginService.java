package com.takeoff.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.domain.UserDetails;
import com.takeoff.model.LoginStatusDTO;
import com.takeoff.repository.CustomerDetailsRepository;
import com.takeoff.repository.UserDetailsRepository;
import com.takeoff.repository.VendorDetailsRepository;

@Service
public class LoginService {
	
	@Autowired
	CustomerDetailsRepository customerDetailsRepository;
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	VendorDetailsRepository vendorDetailsRepository;

	public LoginStatusDTO login(String userid,String password) {
		
		Long id=Long.valueOf(userid);
		
		LoginStatusDTO loginStatus=new LoginStatusDTO();
		
		
		
		Boolean status=false;
		
			
			Optional<UserDetails> user = userDetailsRepository.findByUserIdAndPassword(id, password);
			 status =  user.isPresent();
			 loginStatus.setUserId(userid);
			
				loginStatus.setLoginStatus(status);
				
				if(status)
				loginStatus.setUserType(user.get().getRole().getRoleName());
			
			
		
		
		
		return loginStatus;
	}

}
