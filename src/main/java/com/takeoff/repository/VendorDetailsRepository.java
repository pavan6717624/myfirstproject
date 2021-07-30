package com.takeoff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.takeoff.domain.VendorDetails;

public interface VendorDetailsRepository extends JpaRepository<VendorDetails,Long> {
	
	Optional<VendorDetails> findByVendorIdAndPassword(@Param("vendorId") String vendorId, @Param("password") String password);


}
