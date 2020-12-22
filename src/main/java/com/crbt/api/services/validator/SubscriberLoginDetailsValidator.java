package com.crbt.api.services.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crbt.api.services.bean.LoginWithOtp;
import com.crbt.api.services.domain.SubscriberLoginDetails;

@Component
public class SubscriberLoginDetailsValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return (SubscriberLoginDetails.class.isAssignableFrom(clazz) || LoginWithOtp.class.isAssignableFrom(clazz));
	}

	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
//		SubscriberLoginDetails subscriberLoginDetails = (SubscriberLoginDetails) object;
//
//		if (subscriberLoginDetails.getId() != null) {
//			if (!(subscriberLoginDetails.getId() instanceof Integer))
//				ValidationUtils.rejectIfEmpty(errors, "id", "Invalid value for Subscriber info id.");
//		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "msisdn" , "Msisdn is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "generated_otp" , "Msisdn is requried");
		
		
		
		
	}

}
