package com.crbt.api.services.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crbt.api.services.bean.ChangeAlbumStatusBean;
import com.crbt.api.services.bean.PreviewSongsBean;
import com.crbt.api.services.bean.SearchSongBean;
import com.crbt.api.services.bean.SongRequestBean;
import com.crbt.api.services.bean.SongStatusBean;
import com.crbt.api.services.domain.ContentSongs;

@Component
public class ContentSongsValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return (ContentSongs.class.isAssignableFrom(clazz) || SongStatusBean.class.isAssignableFrom(clazz) || ChangeAlbumStatusBean.class.isAssignableFrom(clazz) || SongRequestBean.class.isAssignableFrom(clazz)|| ContentSongs.class.isAssignableFrom(clazz)
				|| PreviewSongsBean.class.isAssignableFrom(clazz));
	}
	@Override
	public void validate(Object object, Errors errors) {
		ContentSongs contentSongs = (ContentSongs) object;

		if (contentSongs.getId() != null) {
			if (!(contentSongs.getId() instanceof Integer))
				ValidationUtils.rejectIfEmpty(errors, "id", "Invalid value for Subscriber info id.");
		}
		if (contentSongs.getId() == null) {
			if (!(contentSongs.getSongsName() instanceof String))
				ValidationUtils.rejectIfEmpty(errors, "songs_name", "SongName can't be empty.");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "songId" , "SongID is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "songsName" , "Songs Name is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contentAlbum" , "Content Album is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId" , "UserID is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "approverId" , "ApproverID is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "uploaderType" , "UploaderType is requried");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contentPathLocation" , "ContentPathLocation is requried");
		
		
	}

}
