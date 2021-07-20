package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class Controller {
	
	@RequestMapping("/user")
	public String checkProject()
	{
		return "Checked and Worked";
	}
	

}
