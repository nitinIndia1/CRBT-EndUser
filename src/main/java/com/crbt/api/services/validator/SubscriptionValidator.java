package com.crbt.api.services.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.crbt.api.services.bean.ChangeSongsBean;
import com.crbt.api.services.bean.DateRange;
import com.crbt.api.services.bean.SubscriptionRequestBean;
import com.crbt.api.services.domain.Subscription;

@Component
public class SubscriptionValidator  implements Validator{

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return (Subscription.class.isAssignableFrom(clazz) || DateRange.class.isAssignableFrom(clazz) || SubscriptionRequestBean.class.isAssignableFrom(clazz) || ChangeSongsBean.class.isAssignableFrom(clazz));
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
