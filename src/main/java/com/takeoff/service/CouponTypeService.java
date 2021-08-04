package com.takeoff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.domain.CouponType;
import com.takeoff.repository.CouponTypeRepository;

@Service
public class CouponTypeService {
	@Autowired
	CouponTypeRepository couponTypeRepository;
	
	
	public List<CouponType> getCouponTypes()
	{
		return couponTypeRepository.findAll();
	}
	
	public CouponType getCouponType(String couponType)
	{
		return couponTypeRepository.findByCouponType(couponType).get();
	}
	
}
