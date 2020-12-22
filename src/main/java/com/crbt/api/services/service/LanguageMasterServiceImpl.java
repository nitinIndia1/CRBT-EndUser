package com.crbt.api.services.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.crbt.api.services.domain.Language;
import com.crbt.api.services.repository.LanguageMasterRepository;

@Service
@Validated
public class LanguageMasterServiceImpl implements LanguageMasterService{

	private static final Logger logger = LoggerFactory.getLogger(LanguageMasterServiceImpl.class);
	private final LanguageMasterRepository repository;
	

	@Inject
	public LanguageMasterServiceImpl(LanguageMasterRepository repository) {
		super();
		this.repository = repository;
	}


	@Override
	public Language save(Language language) {
		// TODO Auto-generated method stub
		return repository.save(language);
	}


	@Override
	public List<Language> listAllLanguage() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}


	@Override
	public Page<Language> listAllDetails(Pageable page) {
		// TODO Auto-generated method stub
		return repository.findAll(page);
	}
}
