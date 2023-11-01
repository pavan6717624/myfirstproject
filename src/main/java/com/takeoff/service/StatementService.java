package com.takeoff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.model.StatementDTO;
import com.takeoff.repository.StatementRepository;

@Service
public class StatementService {
	
	@Autowired
	StatementRepository statementRepository;

	public List<StatementDTO> takeOffStatement(String customerId) {
		
		return statementRepository.takeOffStatment(Long.valueOf(customerId));
		
		
	}

}
