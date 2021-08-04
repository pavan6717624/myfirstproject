package com.takeoff.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.CustomerMapping;
import com.takeoff.model.DisplayDetailsDTO;
@Repository
public interface CustomerMappingRepository  extends JpaRepository<CustomerMapping,Long>{

	@Query( nativeQuery = true, value = "select user_id as userId,referer_id as refererId,parent_id as parentId from customer_mapping where"
			+ " (parent_id=(:userId) and user_id!=parent_id) or (referer_id=(:userId) and parent_id!=referer_id)"
			+ "  order by user_id asc")
	   public List<DisplayDetailsDTO> getTreeStructure(@Param("userId") Long userId);
	
	
	
	@Query( nativeQuery = true, value ="select user_id as userId from customer_mapping where user_id=parent_id and parent_id=referer_id")
	public Long getRootUserId();
	
	
	@Query("select parentId from CustomerMapping c where c.customer.userId=(:userId)")
	public Long getParentIdForUserId(@Param("userId") Long userId);
}
