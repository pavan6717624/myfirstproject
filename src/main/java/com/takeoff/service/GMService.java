package com.takeoff.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.domain.GMUser;
import com.takeoff.model.GMLoginStatusDTO;
import com.takeoff.model.GMUserDTO;
import com.takeoff.model.GMUserModel;
import com.takeoff.repository.GMRoleRepository;
import com.takeoff.repository.GMUserRepository;

@Service
public class GMService {
	@Autowired
	GMUserRepository userRepository;
	@Autowired
	GMRoleRepository roleRepository;
	
	public GMLoginStatusDTO login(Long contact,String password) {
		
			Optional<GMUser> userOpt = userRepository.findByContactAndPassword(contact,password);
			GMLoginStatusDTO status=new GMLoginStatusDTO();
			status.setLoginStatus(userOpt.isPresent());
			if(userOpt.isPresent())
			{
				status.setUserType(userOpt.get().getRole().getRoleName());
				status.setName(userOpt.get().getName());
			}
		
		return status;
	}

	public List<GMUserDTO> getUsers() {
		// TODO Auto-generated method stub
		List<GMUserDTO> users= userRepository.getUsers();
		System.out.println(users);
		return users;
	}
	
	public List<GMUserDTO> addEmployee(GMUserModel userModel) {
	
		GMUser user = new GMUser();
		user.setName(userModel.getName());
		user.setContact(userModel.getContact());
		user.setEmail(userModel.getEmail());
		user.setCity(userModel.getCity());
		user.setMessage(userModel.getMessage());
		user.setRole(roleRepository.findByRoleName("Employee").get());
		user.setPassword("password");
		userRepository.save(user);
	
		
		return getEmployees();
	}
	public List<GMUserDTO> getEmployees() {
		// TODO Auto-generated method stub
		List<GMUserDTO> users= userRepository.getEmployees();
		System.out.println(users);
		return users;
	}


}
