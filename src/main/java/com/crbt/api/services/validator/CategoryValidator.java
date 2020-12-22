package com.crbt.api.services.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crbt.api.services.bean.CategoryBean;
import com.crbt.api.services.domain.Category;
import com.crbt.api.services.domain.ContentSongs;

@Component
public class CategoryValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return (Category.class.isAssignableFrom(clazz)|| CategoryBean.class.isAssignableFrom(clazz));
	}

	@Override
	public void validate(Object object, Errors errors) {
		Category category = (Category) object;

		if (category.getId() != null) {
			if (!(category.getId() instanceof Integer))
				ValidationUtils.rejectIfEmpty(errors, "id", "Invalid value for Subscriber info id.");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName" , "Category Name is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isActive" , "Status is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "priority" , "priority is requried");
		
		
	}

}
