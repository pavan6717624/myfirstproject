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
			+ "i.id as id, i.keywords as keywords,i.user.userId as userId, i.subCateogry.category.id as categoryId  from ImageDetails i where "
			+ "(i.user.userId = (:userId) or i.user.role.roleName like 'Designer') "
			+ " and i.id not in (:imageIds) and i.deleted=false and (i.subCateogry.category.id=(:category) or (:category=0)) and "
			+" (i.subCateogry.id=(:subCategory) or (:subCategory=0)) and "
			+ "( i.keywords like :keyword1 or i.keywords like :keyword2 or i.keywords like :keyword3 or "
			+ " i.keywords like :keyword4 or i.keywords like :keyword5 ) "
			+ " order by id desc")
	List<ImageDetailsDTO> findByLatest(@Param("userId") Long userid,
			@Param("imageIds") List<Long> imageIds, 
			@Param("category") Long category,
			@Param("subCategory") Long subCategory,
			@Param("keyword1") String keyword1,
			@Param("keyword2") String keyword2,
			@Param("keyword3") String keyword3,
			@Param("keyword4") String keyword4,
			@Param("keyword5") String keyword5,
			Pageable pageable);

}
