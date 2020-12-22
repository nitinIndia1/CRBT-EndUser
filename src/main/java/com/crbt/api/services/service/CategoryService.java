package com.crbt.api.services.service;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crbt.api.services.domain.Category;

public interface CategoryService {

	Category update(@Valid @NotNull final Category category);

	Category save(@Valid @NotNull final  Category category);

	void delteCategoryById(@Valid @NotNull final Integer id);

	Category getCategoryIDByid(@Valid @NotNull  Integer id);

	Page<Category> listAllCategory(Pageable page);

	Category getCategoryById(@Valid @NotNull final Integer id);

	List<Category> getCategoryByName(String category_name ,String category_name_ar);

	List<Category> listAllCategoryByPriority();

	List<Object> getCategoryOrderByPriority();

	
	}
