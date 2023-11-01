package com.takeoff.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.takeoff.domain.UserDetails;
import com.takeoff.model.DisplayItemsDTO;
import com.takeoff.model.OrderDetails;

@Service
public class OrderService {

	@Transactional
	public Boolean payment(OrderDetails orderDetails)
	{
		
		System.out.println(orderDetails);
		
		return true;
		
		
		
		
		
	}


	
}
