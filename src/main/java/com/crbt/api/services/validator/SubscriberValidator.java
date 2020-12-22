package com.crbt.api.services.validator;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crbt.api.services.bean.LanguageChangeBean;
import com.crbt.api.services.bean.LoginWithPassword;
import com.crbt.api.services.bean.PasswordChangeBean;
import com.crbt.api.services.bean.SubscriberProfileBean;
import com.crbt.api.services.domain.Subscriber;

@Component
public class SubscriberValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return (Subscriber.class.isAssignableFrom(clazz) || PasswordChangeBean.class.isAssignableFrom(clazz) || LoginWithPassword.class.isAssignableFrom(clazz)|| SubscriberProfileBean.class.isAssignableFrom(clazz)|| LanguageChangeBean.class.isAssignableFrom(clazz));
	}

	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		Subscriber subscriber = (Subscriber) object;

		if (subscriber.getId() != null) {
			if (!(subscriber.getId() instanceof Integer))
				ValidationUtils.rejectIfEmpty(errors, "id", "Invalid value for Subscriber info id.");
		}
		/*if( subscriber.getId() ==null){
			if(!((subscriber.getCreatedOn()) instanceof Date)){
				
				ValidationUtils.rejectIfEmpty(errors, "createdOn", "CreatedDate is requried");
			}
		}
	
		if( subscriber.getId() !=null){
			if( subscriber.getCreatedOn() != null ){
				//errors.reject("400", "Created date can not be updated");
				errors.rejectValue("createdOn", "Created date can not be updated");
				//ValidationUtils.reject(errors, "createdDate", "CreatedDate can not  be updated ");
			}
		}
		if( subscriber.getId() !=null){
			if(!((subscriber.getUpdatedOn()) instanceof Date)){
				
				ValidationUtils.rejectIfEmpty(errors, "updatedOn", "UpdatedDate is requried");
			}
		}
	
		
		if( subscriber.getId() ==null){
			if( subscriber.getUpdatedOn() != null ){
				errors.rejectValue("updatedOn", "Updated date can not be requried");
				//ValidationUtils.reject(errors, "createdDate", "CreatedDate can not  be updated ");
			}
		}*/
	
    
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "msisdn", "Msisdn is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password" , "password is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email" , "Email is requried");
		
	}

}
