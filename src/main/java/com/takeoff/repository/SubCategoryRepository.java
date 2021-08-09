package com.takeoff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.takeoff.domain.Category;
import com.takeoff.domain.SubCategory;
import com.takeoff.model.SubCategoryDTO;

@Repository
public interface SubCategoryRepository  extends JpaRepository<SubCategory,Long> {

	@Query("select s from SubCategory s where s.subCategoryName=(:subCategory) and s.isDeleted=false and s.category.isDeleted=false")
	Optional<SubCategory> findBySubCategoryName(String subCategory);
	
	@Query("select s from SubCategory s where s.category=(:category) and s.isDeleted=false and s.category.isDeleted=false")
	List<SubCategory> findByCategory(Category category);

	@Query("select c.id as categoryId,c.categoryName as categoryName, "
			+ "c.categoryVisibility as categoryVisibility, c.isDeleted as isCategoryDeleted, "
			+ "s.id as subCategoryId, s.subCategoryName as subCategoryName,  "
			+ "s.subCategoryVisibility as subCategoryVisibility, s.isDeleted as isSubCategoryDeleted, s.id as subCategoryId, "
			+ "s.mandatoryComplimentary as mandatoryComplimentary "
			+ " from Category c left join SubCategory s on s.category=c where c.isDeleted = false"
			+ " and (s.isDeleted=null or s.isDeleted=false) order by c.id desc")
	List<SubCategoryDTO> getAllSubCategories();
	
	@Query( "select c.id as categoryId,c.categoryName as categoryName, c.categoryVisibility as categoryVisibility, c.isDeleted as isCategoryDeleted"
			+ " from Category c where c.isDeleted=false and c in "
			+ "(select s.category from SubCategory s group by s.category having sum(case when isDeleted=true then 1 else 0 end)=count(*))")
	List<SubCategoryDTO> getAllSubCategoriesAppend();
	

}
