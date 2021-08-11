package com.takeoff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.CouponType;

@Repository
public interface CouponTypeRepository extends JpaRepository<CouponType,Long> {
	
	@Query("select c from CouponType c where c.isDeleted=false and c.couponType=(:couponType)")
	Optional<CouponType> findByCouponType(String couponType);
 
	@Query("select c from CouponType c where c.isDeleted=false order by c.id desc")
	List<CouponType> getCouponTypes();



}
