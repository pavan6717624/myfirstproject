package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class MyfirstprojectApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MyfirstprojectApplication.class, args);
	}

	@RequestMapping("/")
	public String demo()
	{
		return "{\"demo\":\"dmeoadsfasdfasd\"}";
	}
}
