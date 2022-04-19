package com.takeoff.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.CustomerDetails;
import com.takeoff.model.CustomerDetailsDTO;
import com.takeoff.model.GstDetails;
import com.takeoff.model.StatsDTO;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails,Long> {
	
	Optional<CustomerDetails> findByReferCode(String referCode);
	
	  @Modifying
	  @Transactional
	    @Query( nativeQuery = true, value = "insert into customer_mapping(user_id,referer_id,parent_id,type) select (:userId) as user_id,  (:refererId) as referer_id,(case when (:type)='Free' then (:refererId) else case when (count(*) > 0 && (count(*)=1 || count(*) = 3 || count(*) = 5 ||(count(*)-5)%5=0)) then (select parent_id from customer_mapping where user_id like (:refererId)) else (:refererId) end end)   as parent_id,(:type) from customer_mapping where referer_id like (:refererId) and type like 'Pay'")
	    public int customerMapping(@Param("userId") Long userId, @Param("refererId") Long refererId,@Param("type") String type);
	  
	  @Modifying
	  @Transactional
	    @Query( nativeQuery = true, value = "UPDATE customer_mapping AS s, (select (:userId) as user_id, (case when (count(*) > 0 && (count(*)=1 || count(*) = 3 || count(*) = 5 ||(count(*)-5)%5=0)) then (select parent_id from customer_mapping where user_id like (:refererId)) else (:refererId) end) as parent_id from customer_mapping where referer_id like (:refererId) and type like 'Pay') AS p SET s.parent_id = p.parent_id WHERE s.user_id = p.user_id")
	    public int upgradeCustomerMapping(@Param("userId") Long userId, @Param("refererId") Long refererId);
	  

	  @Query("select c from CustomerDetails c where c.user.userId=(:userId)")
		Optional<CustomerDetails> findByUserId(@Param("userId") Long userId);
	  
	  
	  @Query("select c.user.loginId as userId,DATE_FORMAT(c.user.joinDate, '%d %M %Y %h:%i:%s %p') as joinDate, c.user.name as name,c.user.contact as contact,c.user.email as email,c.user.city as city,c.profession as profession,c.gender as gender,c.razorpay_payment_id as razorpay_payment_id,c.razorpay_order_id as razorpay_order_id,c.refererId as refererId,c.referCode as referCode,c.paymentStatus as paymentStatus,c.mappingStatus as mappingStatus,c.walletAmount as walletAmount from CustomerDetails c where c.user.userId=(:userId)")
		CustomerDetailsDTO getCustomerAccountDetails(@Param("userId") Long userId);

	  @Query("select c.user.userId as id,c.user.name as name,c.user.joinDate as date from CustomerDetails c where c.user.userId not in (10001,10147) and c.user.type='Pay' order by c.user.joinDate asc")
	  List<GstDetails> gstDetails();
	  
	  
	  
	  @Query("select c.user.userId as userId,(case when (c.user.type like 'Free' and Upper(c.refererId) in (:special)) then 'Special' else c.user.type end) as type, DATE_FORMAT(c.user.joinDate, '%d %M %Y %h:%i:%s %p') as joinDate, c.user.name as name,c.user.contact as contact,c.user.email as email,c.user.city as city,c.profession as profession,c.gender as gender,c.razorpay_payment_id as razorpay_payment_id,c.razorpay_order_id as razorpay_order_id,c.refererId as refererId,c.referCode as referCode,c.paymentStatus as paymentStatus,c.mappingStatus as mappingStatus,c.walletAmount as walletAmount from CustomerDetails c order by c.user.userId asc")
		List<CustomerDetailsDTO> getAllCustomerAccountDetails(@Param("special") List special);
	
	  
	  @Query("select c.user.userId as userId,DATE_FORMAT(c.user.joinDate, '%d %M %Y %h:%i:%s %p') as joinDate, c.user.name as name,c.user.contact as contact,c.user.email as email,c.user.city as city,c.profession as profession,c.gender as gender,c.refererId as refererId,c.referCode as referCode from CustomerDetails c where c.user.joinDate >= (select u.joinDate from UserDetails u where u.userId=(:userId)) order by c.user.userId desc")
		List<CustomerDetailsDTO> getInvestorCustomerAccountDetails(@Param("userId") Long userId);
	  
	  
	  @Query("select u.role.roleName as roleName, u.type as type,  sum(case when DATE(u.joinDate)=CURDATE() and month(u.joinDate)=month(current_date) and YEAR(u.joinDate) = YEAR(CURRENT_DATE) then 1 else 0 end) as todayCount, sum(case when month(u.joinDate)=month(current_date) and YEAR(u.joinDate) = YEAR(CURRENT_DATE) then 1 else 0 end) as monthCount, count(*) as totalCount from UserDetails u where u.role.id!=1 group by u.role.roleName, u.type")
		List<StatsDTO> getUserStats();

	  
	  @Query("select sum(walletAmount) from CustomerDetails")
			Long getWalletBalance();
	  
	  @Query("select c.user.userId as userId,DATE_FORMAT(c.user.joinDate, '%d %M %Y %h:%i:%s %p') as joinDate, c.user.name as name,c.user.contact as contact,c.user.email as email,c.user.city as city,c.profession as profession,c.gender as gender,c.refererId as refererId,c.referCode as referCode from CustomerDetails c where c.executive.userId=(:executiveId) order by c.user.userId desc")
		List<CustomerDetailsDTO> getExecutiveCustomerAccountDetails(@Param("executiveId") Long executiveId);

	  @Query("select count(c) from CustomerDetails c where c.refererId=(:refererId) and MONTH(c.user.joinDate) = MONTH(CURRENT_DATE) and YEAR(c.user.joinDate) = YEAR(CURRENT_DATE) and c.user.type like 'Free' and c.user.isDeleted!=true and c.user.isDisabled!=true")
	  Long getFreeSubscriptionsCount(String refererId);
	  
	  @Query("select count(c) from CustomerDetails c where c.refererId=(:refererId) and MONTH(c.user.joinDate) = MONTH(CURRENT_DATE) and YEAR(c.user.joinDate) = YEAR(CURRENT_DATE) and c.user.type like 'Pay' and c.user.isDeleted!=true and c.user.isDisabled!=true")
	  Long getPremiumSubscriptionsCount(String refererId);
}
