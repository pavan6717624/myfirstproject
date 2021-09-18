package com.takeoff.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.takeoff.domain.CouponType;
import com.takeoff.repository.CouponTypeRepository;
import com.takeoff.repository.VendorCouponsRepository;

@Service
public class CouponTypeService {
	@Autowired
	CouponTypeRepository couponTypeRepository;
	
	@Autowired
	VendorCouponsRepository vendorCouponsRepository;
	public Boolean complimentaryExists() {
	Boolean status=false;
	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	
	
	if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Vendor")) && vendorCouponsRepository.complimentaryExists(Long.valueOf(userDetails.getUsername())))
	return true;
	return status;
	}
	public List<CouponType> getCouponTypes()
	{
	if(complimentaryExists())
	return couponTypeRepository.getCouponTypes();
	else
	return Arrays.asList(couponTypeRepository.findById(1l).get());
	}
	
	public List<CouponType> getAllCouponTypes()
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
