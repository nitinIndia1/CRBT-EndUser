package com.crbt.api.services.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crbt.api.services.domain.Language;

public interface LanguageMasterService {
	
	Language save(Language languageMaster);

	List<Language> listAllLanguage();

	Page<Language> listAllDetails(Pageable page);

}
