package com.takeoff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.takeoff.domain.VendorCoupons;

public interface VendorCouponsRepository  extends JpaRepository<VendorCoupons,Long> {
	
	@Query("select c from VendorCoupons c where c.vendor.user.userId = (:userId) order by id desc")
	List<VendorCoupons> findByLatest(@Param("userId") Long userId);
}
