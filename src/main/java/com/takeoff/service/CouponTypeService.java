package com.takeoff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.domain.Category;
import com.takeoff.domain.CouponType;
import com.takeoff.repository.CouponTypeRepository;

@Service
public class CouponTypeService {
	@Autowired
	CouponTypeRepository couponTypeRepository;
	
	
	public List<CouponType> getCouponTypes()
	{
		return couponTypeRepository.getCouponTypes();
	}
	
	public CouponType getCouponType(String couponType)
	{
		return couponTypeRepository.findByCouponType(couponType).get();
	}

	public Boolean visibleCouponType(Long couponTypeId) {
		CouponType ct=couponTypeRepository.findById(couponTypeId).get();

		
		ct.setCouponTypeVisibility(!ct.getCouponTypeVisibility());

		ct = couponTypeRepository.save(ct);
		

		
		if(ct.getId()!=null)
			return true;
		else
			return false;
		
	}

	public Boolean deleteCouponType(Long couponTypeId) {
		
		CouponType ct=couponTypeRepository.findById(couponTypeId).get();

		
		ct.setIsDeleted(true);

		ct = couponTypeRepository.save(ct);
		

		
		if(ct.getId()!=null)
			return true;
		else
			return false;
	}

	public Boolean addCouponType(String couponTypeName) {
		CouponType cat=new CouponType();
		cat.setCouponType(couponTypeName);
		cat.setCouponTypeVisibility(true);
		cat.setIsDeleted(false);
		cat = couponTypeRepository.save(cat);
		
		if(cat.getId()!=null)
			return true;
		else
			return false;
	}

	public Boolean editCouponType(Long couponTypeId, String couponTypeName) {
		CouponType cat=couponTypeRepository.findById(couponTypeId).get();
		cat.setCouponType(couponTypeName);
		cat = couponTypeRepository.save(cat);
		
		if(cat.getId()!=null)
			return true;
		else
			return false;
	}
	
}
