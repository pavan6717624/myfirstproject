package com.takeoff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.Category;
import com.takeoff.domain.SubCategory;

@Repository
public interface SubCategoryRepository  extends JpaRepository<SubCategory,Long> {

	Optional<SubCategory> findBySubCategoryName(String subCategory);
	
	List<SubCategory> findByCategory(Category category);

}
