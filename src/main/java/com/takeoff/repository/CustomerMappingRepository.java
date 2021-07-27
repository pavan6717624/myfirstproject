package com.takeoff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.takeoff.domain.CustomerMapping;
import com.takeoff.model.DisplayDetailsDTO;

public interface CustomerMappingRepository  extends JpaRepository<CustomerMapping,Long>{

	@Query( nativeQuery = true, value = "select customer_id as customerId,referer_id as refererId,parent_id as parentId from customer_mapping where"
			+ " (parent_id=(:customerId) and customer_id!=parent_id) or (referer_id=(:customerId) and parent_id!=referer_id)"
			+ "  order by customer_id asc")
	   public List<DisplayDetailsDTO> getTreeStructure(@Param("customerId") Long customerId);
	
	
	
	@Query( nativeQuery = true, value ="select customer_id as customerId from customer_mapping where customer_id=parent_id and parent_id=referer_id")
	public Long getRootCustomerId();
	
	
	@Query("select parentId from CustomerMapping c where c.customer.customerId=(:customerId)")
	public Long getParentIdForCustomerId(@Param("customerId") Long customerId);
}
