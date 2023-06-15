package com.takeoff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.takeoff.model.GMLoginStatusDTO;
import com.takeoff.model.GMUserDTO;
import com.takeoff.model.GMUserModel;
import com.takeoff.service.GMService;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "GMAPI")
public class GMController {
	@Autowired
	GMService service;
	
	
	@RequestMapping(value = "login")
	public GMLoginStatusDTO login(@RequestParam("contact") String contact, @RequestParam("password") String password)
	{
		System.out.println("came00");
		return service.login(Long.valueOf(contact), password);
	}
	
	@RequestMapping(value = "getUsers")
	public List<GMUserDTO> getUsers()
	{
		System.out.println("came00");
		return service.getUsers();
	}
	
	@RequestMapping(value = "addEmployee")
	public List<GMUserDTO> addEmployee(@RequestBody GMUserModel user)
	{
		System.out.println("came00");
		return service.addEmployee(user);
	}
	
	@RequestMapping(value = "getEmployees")
	public List<GMUserDTO> getEmployees()
	{
		System.out.println("came00");
		return service.getEmployees();
	}


}
