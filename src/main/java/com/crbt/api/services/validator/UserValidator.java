package com.crbt.api.services.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.crbt.api.services.domain.User;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		User user = (User) object;

		if (user.getId() != null) {
			if (!(user.getId() instanceof Integer))
				ValidationUtils.rejectIfEmpty(errors, "id", "Invalid value for user info id.");
		}
		
		
		// if(user.getId()!=null){
		// if(!(user.getMobileNo() instanceof String))
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Name is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "EMail No is requried");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobileNo", "Mobile No is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isActive", "Status No is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyName", "Company Name No is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "Location No is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "role", "Role Id No is requried");

		// }
	}
}
