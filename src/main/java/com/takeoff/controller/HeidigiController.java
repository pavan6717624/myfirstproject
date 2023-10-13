package com.takeoff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.takeoff.model.GMLoginStatusDTO;
import com.takeoff.model.HeidigiLoginDTO;
import com.takeoff.model.HeidigiSignupDTO;
import com.takeoff.service.HeidigiService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "Heidigi")
public class HeidigiController {
	
	
	@Autowired
	HeidigiService service;
	
	@RequestMapping(value = "check")
	public GMLoginStatusDTO check()
	{
		System.out.println("came00000 ");
		return null;
	}
	
	@RequestMapping(value = "login")
	public Boolean login(@RequestBody HeidigiLoginDTO login)
	{
		return service.login(login);
		
	}
	
	@RequestMapping(value = "signup")
	public Boolean signup(@RequestBody HeidigiSignupDTO signup)
	{
		return service.signup(signup);
	}

}
