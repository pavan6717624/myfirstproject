package com.takeoff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.takeoff.domain.VendorDetails;

public interface VendorDetailsRepository extends JpaRepository<VendorDetails,Long> {
	
	
	@Query("select v from VendorDetails v where v.user.userId=(:userId)")
	Optional<VendorDetails> findByUserId(@Param("userId") Long userId);


}
