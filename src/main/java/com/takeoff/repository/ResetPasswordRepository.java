package com.takeoff.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.takeoff.domain.ResetPassword;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword,Long> {
	
	@Query("select passcode from ResetPassword r where (r.customer.userId=(:customerId) or r.customer.loginId=(:customerId)) and r.validTill >= (:currentTime)")
	
	Long getPasscode(@Param("customerId") Long customerId, @Param("currentTime") Timestamp currentTime);

}
