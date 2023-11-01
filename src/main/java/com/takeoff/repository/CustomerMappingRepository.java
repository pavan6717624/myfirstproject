package com.takeoff.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.CustomerMapping;
import com.takeoff.domain.UserDetails;
import com.takeoff.model.DisplayDetailsDTO;
import com.takeoff.model.TdsDTO;
@Repository
public interface CustomerMappingRepository  extends JpaRepository<CustomerMapping,Long>{

	@Query( nativeQuery = true, value = "select user_id as customerId,referer_id as refererId,parent_id as parentId from customer_mapping where"
			+ " (parent_id=(:userId) and user_id!=parent_id) or (referer_id=(:userId) and parent_id!=referer_id)"
			+ "  order by user_id asc")
	   public List<DisplayDetailsDTO> getTreeStructure(@Param("userId") Long userId);
	
	@Query( nativeQuery = true, value = "select m.user_id as customerId,m.referer_id as refererId,parent_id as parentId, "
			+ "d.refer_code as referCode, u.name as name from customer_mapping m,customer_details d,user_details u where m.user_id = d.user_id and u.user_id=d.user_id order by m.user_id asc")
	   public List<DisplayDetailsDTO> getTreeStructureNew();
	
	
	@Query( nativeQuery = true, value ="select user_id as userId from customer_mapping where user_id=parent_id and parent_id=referer_id")
	public Long getRootUserId();
	
	
	@Query("select parentId from CustomerMapping c where c.customer.userId=(:userId)")
	public Long getParentIdForUserId(@Param("userId") Long userId);
	
	
	@Query("select c.parentId as parentId, d.user.name as name, k.pan as pan, d.user.email as email, d.user.contact as contact, d.user.city as city, (count(*)*(case when k.panStatus like 'Approved' then 25 else 100 end))  as tds from CustomerMapping c, CustomerDetails d, KYCDetails k where k.customer.customerId = d.customerId and d.user.userId=c.parentId and c.customer.joinDate >= (:fromDate) and c.customer.joinDate <= (:toDate) group by parentId, d.user.name , d.user.email, d.user.contact, d.user.city order by parentId, d.user.name , d.user.email, d.user.contact, d.user.city desc")
	public List<TdsDTO> getTDS(@Param("fromDate") Timestamp fromDate, @Param("toDate") Timestamp toDate);
	
	Optional<CustomerMapping> findByCustomer(@Param("customer") UserDetails customer);
	
}
