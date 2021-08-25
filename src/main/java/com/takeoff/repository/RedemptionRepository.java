package com.takeoff.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.Redemption;

@Repository
public interface RedemptionRepository extends JpaRepository<Redemption,Long> {

	@Query("select r from Redemption r where r.coupon.id=(:couponId) and r.vendor.userId=(:vendorId) and r.customer.userId=(:customerId) and r.validTill >= (:currentTime)")
	Redemption findPasscode(@Param("couponId") Long couponId,@Param("customerId")  Long customerId, @Param("vendorId")  Long vendorId, @Param("currentTime") Timestamp currentTime);


	
}
