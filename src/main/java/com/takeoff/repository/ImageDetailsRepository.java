package com.takeoff.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.ImageDetails;
import com.takeoff.model.ImageDetailsDTO;

@Repository
public interface ImageDetailsRepository  extends PagingAndSortingRepository<ImageDetails,Long> 

{
	
	@Query("select concat('data:image/jpeg;base64,',i.image) as image, "
			+ "i.id as id, i.user.userId as userId  from ImageDetails i where "
			+ "(i.user.userId = (:userId) or i.user.role.roleName like 'Designer') "
			+ " and i.id not in (:imageIds) and i.deleted=false "
			+ "order by id desc")
	List<ImageDetailsDTO> findByLatest(@Param("userId") Long userid,@Param("imageIds") List<Long> imageIds, Pageable pageable);

}
