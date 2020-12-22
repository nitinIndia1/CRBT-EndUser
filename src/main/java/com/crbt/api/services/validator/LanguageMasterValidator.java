package com.crbt.api.services.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crbt.api.services.domain.Language;

@Component
public class LanguageMasterValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Language.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		Language languageMaster = (Language) object;

		if (languageMaster.getId() != null) {
			if (!(languageMaster.getId() instanceof Integer))
				ValidationUtils.rejectIfEmpty(errors, "id", "Invalid value for languageMaster info id.");
		}
		
		
	}
}
