package com.takeoff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.domain.Category;
import com.takeoff.domain.SubCategory;
import com.takeoff.model.SubCategoryDTO;
import com.takeoff.model.VendorDetailsDTO;
import com.takeoff.repository.CategoryRepository;
import com.takeoff.repository.SubCategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	SubCategoryRepository subCategoryRepository;
	
	public List<Category> getCategories()
	{
		return categoryRepository.findAllCategories();
	}
	
	public List<SubCategory> getSubCategories(String category)
	{
		try
		{
		return subCategoryRepository.findByCategory(categoryRepository.findById(Long.valueOf(category)).get());
		}
		catch(Exception ex)
		{
		return subCategoryRepository.findByCategory(categoryRepository.findByCategoryName(category).get());
	}
		}

	public List<SubCategoryDTO> getAllSubCategories() {
		
		List<SubCategoryDTO> list = subCategoryRepository.getAllSubCategories();
		list.addAll(subCategoryRepository.getAllSubCategoriesAppend());
		return list;
	}

	public Boolean addCategory(String category) {
		
		Category cat=new Category();
		cat.setCategoryName(category);
		cat.setCategoryVisibility(true);
		cat.setIsDeleted(false);
		cat = categoryRepository.save(cat);
		
		if(cat.getId()!=null)
			return true;
		else
			return false;
		
		
	}
	
public Boolean addSubCategory(Long categoryId, String subcategory) {
		
		Category cat=categoryRepository.findById(categoryId).get();
		
		SubCategory scat=new SubCategory();
		scat.setCategory(cat);
		scat.setSubCategoryName(subcategory);
		scat.setSubCategoryVisibility(true);
		scat.setIsDeleted(false);
		scat.setMandatoryComplimentary(true);
		
		scat=subCategoryRepository.save(scat);
		
		
		if(scat.getId()!=null)
			return true;
		else
			return false;
		
		
	}

public Boolean deleteSubCategory(Long subcategoryId) {
	
	SubCategory scat=subCategoryRepository.findById(subcategoryId).get();
	
	scat.setIsDeleted(true);
	
	scat=subCategoryRepository.save(scat);
	
	
	if(scat.getId()!=null)
		return true;
	else
		return false;
	
}

public Boolean deleteCategory(Long categoryId) {

	Category cat=categoryRepository.findById(categoryId).get();

	
	cat.setIsDeleted(true);

	cat = categoryRepository.save(cat);
	

	
	if(cat.getId()!=null)
		return true;
	else
		return false;
	
}

public Boolean editCategory(Long categoryId, String categoryName) {
Category cat=categoryRepository.findById(categoryId).get();

	
	cat.setCategoryName(categoryName);

	cat = categoryRepository.save(cat);
	

	
	if(cat.getId()!=null)
		return true;
	else
		return false;
	
}

public Boolean editSubCategory(Long subcategoryId, String subCategoryName) {
	
SubCategory scat=subCategoryRepository.findById(subcategoryId).get();
	
	scat.setSubCategoryName(subCategoryName);
	
	scat=subCategoryRepository.save(scat);
	
	
	if(scat.getId()!=null)
		return true;
	else
		return false;
}

public Boolean visibleSubCategory(Long subcategoryId) {
SubCategory scat=subCategoryRepository.findById(subcategoryId).get();
	
	scat.setSubCategoryVisibility(!scat.getSubCategoryVisibility());
	
	scat=subCategoryRepository.save(scat);
	
	
	if(scat.getId()!=null)
		return true;
	else
		return false;
}

public Boolean visibleCategory(Long categoryId) {
	
Category cat=categoryRepository.findById(categoryId).get();

	
	cat.setCategoryVisibility(!cat.getCategoryVisibility());

	cat = categoryRepository.save(cat);
	

	
	if(cat.getId()!=null)
		return true;
	else
		return false;
}

public Boolean mandatoryComplimentaryChange(Long subCategoryId) {
SubCategory scat=subCategoryRepository.findById(subCategoryId).get();
	
	scat.setMandatoryComplimentary(!scat.getMandatoryComplimentary());
	
	scat=subCategoryRepository.save(scat);
	
	
	if(scat.getId()!=null)
		return true;
	else
		return false;
}

}
