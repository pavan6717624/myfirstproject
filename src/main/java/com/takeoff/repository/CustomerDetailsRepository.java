package com.takeoff.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.CustomerDetails;
import com.takeoff.model.CustomerDetailsDTO;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails,Long> {
	
	Optional<CustomerDetails> findByReferCode(String referCode);
	
	  @Modifying
	  @Transactional
	    @Query( nativeQuery = true, value = "insert into customer_mapping(user_id,referer_id,parent_id) select (:userId) as user_id,  (:refererId) as referer_id,(case when (count(*) > 0 && (count(*)=1 || count(*) = 3 || count(*) = 5 ||(count(*)-5)%5=0)) then (select parent_id from customer_mapping where user_id like (:refererId)) else (:refererId) end)  as parent_id from customer_mapping where referer_id like (:refererId)")
	    public int customerMapping(@Param("userId") Long userId, @Param("refererId") Long refererId);
	  
	  
	

	  @Query("select c from CustomerDetails c where c.user.userId=(:userId)")
		Optional<CustomerDetails> findByUserId(@Param("userId") Long userId);
	  
	  
	  @Query("select c.user.userId as userId,c.user.name as name,c.user.contact as contact,c.user.email as email,c.user.city as city,c.profession as profession,c.gender as gender,c.razorpay_payment_id as razorpay_payment_id,c.razorpay_order_id as razorpay_order_id,c.refererId as refererId,c.referCode as referCode,c.paymentStatus as paymentStatus,c.mappingStatus as mappingStatus,c.walletAmount as walletAmount from CustomerDetails c where c.user.userId=(:userId)")
		CustomerDetailsDTO getCustomerAccountDetails(@Param("userId") Long userId);


}
