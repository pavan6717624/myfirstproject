package com.takeoff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.CouponType;

@Repository
public interface CouponTypeRepository extends JpaRepository<CouponType,Long> {

	Optional<CouponType> findByCouponType(String couponType);



}
