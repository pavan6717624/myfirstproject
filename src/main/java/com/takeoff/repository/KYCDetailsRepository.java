package com.takeoff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.KYCDetails;
import com.takeoff.model.KYCDetailsDTO;

@Repository
public interface KYCDetailsRepository  extends JpaRepository<KYCDetails,Long> {

	
	@Query("select k.id as id, k.customer.user.name as name, k.customer.user.userId as customerId, "
		+ "k.customer.user.contact as contact,  k.pan as pan, k.cname as cname, k.bname as bname, k.ifsc as ifsc,"
		+ " k.customer.walletAmount as walletAmount from KYCDetails k where k.customer.user.userId=(:customerId) or (:customerId)=0")
	List<KYCDetailsDTO> getKYCDetails(@Param("customerId") Long customerId);
	
	

}
