package com.takeoff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.domain.Category;
import com.takeoff.domain.SubCategory;
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
		return categoryRepository.findAll();
	}
	
	public List<SubCategory> getSubCategories(String category)
	{
		return subCategoryRepository.findByCategory(categoryRepository.findByCategoryName(category).get());
	}

}
