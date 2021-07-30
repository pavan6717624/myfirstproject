package com.takeoff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.CouponDetails;

@Repository
public interface CouponDetailsRepository  extends JpaRepository<CouponDetails,Long> {

}
