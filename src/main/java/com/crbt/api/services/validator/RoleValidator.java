package com.crbt.api.services.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crbt.api.services.domain.Role;

@Component
public class RoleValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Role.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		Role role = (Role) object;

		if (role.getId() != null) {
			if (!(role.getId() instanceof Integer))
				ValidationUtils.rejectIfEmpty(errors, "id", "Invalid value for role info id.");
		}

	}

}
