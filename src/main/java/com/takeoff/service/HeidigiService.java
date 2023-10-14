package com.takeoff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.domain.HeidigiUser;
import com.takeoff.model.HeidigiLoginDTO;
import com.takeoff.model.HeidigiSignupDTO;
import com.takeoff.repository.HeidigiRoleRepository;
import com.takeoff.repository.HeidigiUserRepository;

@Service
public class HeidigiService {
	
	@Autowired
	HeidigiUserRepository userRepository;
	@Autowired
	HeidigiRoleRepository roleRepository;
	
	
	public Boolean signup(HeidigiSignupDTO signup) {
		
		HeidigiUser user=new HeidigiUser();
		user.setEmail(signup.getEmail());
		user.setMobile(Long.valueOf(signup.getMobile()));
		user.setName(signup.getName());
		user.setPassword(signup.getPassword());
		user.setMessage("Customer Signup");
		user.setRole(roleRepository.findByRoleName("Customer").get());
		
		userRepository.save(user);
		
		
		
		return userRepository.findByMobileAndPassword(user.getMobile(), user.getPassword()).isPresent();
	}

	public Boolean login(HeidigiLoginDTO login) {
		
		
	
		return userRepository.findByMobileAndPassword(Long.valueOf(login.getMobile()), login.getPassword()).isPresent();
		
	}

	public List<String> getImages() {
		
		return userRepository.getRandomImages();
	}

}
