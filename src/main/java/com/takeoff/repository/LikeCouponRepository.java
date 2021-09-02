package com.takeoff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.LikeCoupons;

@Repository
public interface LikeCouponRepository  extends JpaRepository<LikeCoupons,Long> {

	@Query("select sum(case when likeCoupon=true then 1 else 0 end) from LikeCoupons l where l.coupon.id=(:couponId)")
	Long getLikes(@Param("couponId") Long couponId);
	
	@Query("select sum(case when disLikeCoupon=true then 1 else 0 end) from LikeCoupons l where l.coupon.id=(:couponId)")
	Long getDisLikes(@Param("couponId") Long couponId);

	@Query("select l from LikeCoupons l where l.coupon.id=(:couponId) and l.customer.userId=(:userId)")
	Optional<LikeCoupons> getCouponDetails(@Param("couponId") Long couponId,@Param("userId") Long userId);

}
