package com.crbt.api.services.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crbt.api.services.bean.AlbumUploadImageBean;
import com.crbt.api.services.domain.ContentAlbum;

@Component
public class ContentAlbumValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return ContentAlbum.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		ContentAlbum contentAlbum = (ContentAlbum) object;

		if (contentAlbum.getId() != null) {
			if (!(contentAlbum.getId() instanceof Integer))
				ValidationUtils.rejectIfEmpty(errors, "id", "Invalid value for Subscriber info id.");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "albumTitle" , "Album Title is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "albumDescription" , "Album Description is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "albumGenre" , "Album Genre is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "albumArtist" , "Album Artist is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "albumCategory" , "Album Category is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "albumArt" , "album Art is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "albumType" , "album Type is requried");
		
		
	}

}
