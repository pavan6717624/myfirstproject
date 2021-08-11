package com.takeoff;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class TakeOff {
	  
	
	public static void main(String[] args) {
		SpringApplication.run(TakeOff.class, args);
	}

	@RequestMapping("/")
	public String demo()
	{
		return "{\"demo\":\"dmeoadsfasdfasd\"}";
	}
}
