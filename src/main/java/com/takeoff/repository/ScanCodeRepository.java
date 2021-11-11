package com.takeoff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.ScanCode;
import com.takeoff.model.ScanCodeDTO;

@Repository
public interface ScanCodeRepository extends JpaRepository<ScanCode,Long> {

	Optional<ScanCode> findByCode(@Param("scanCode") String scanCode);
	
	@Query("select s from ScanCode s where code like (:scanCode) and s.vendor.vendorid=(:vendorId)")
	Optional<ScanCode> findByCodeAndVendorId(@Param("scanCode") String scanCode, @Param("vendorId") Long vendorId);
	
	@Query("select (case when s.code != null then 'Captured' else 'Not Captured' end) as code, v.user.loginId as vendorId,v.user.name as vendorName from ScanCode s right join VendorDetails v on s.vendor = v order by code, vendorId")
	List<ScanCodeDTO> getCodes();

}
