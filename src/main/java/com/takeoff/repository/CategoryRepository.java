package com.takeoff.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
	@Query("select c from Category c where c.categoryName=(:category) and c.isDeleted=false")
	Optional<Category> findByCategoryName(String category);
	@Query("select c from Category c where c.isDeleted=false")
	List<Category> findAllCategories();

}
