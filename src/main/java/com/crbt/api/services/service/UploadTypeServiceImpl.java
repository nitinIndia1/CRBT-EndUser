package com.crbt.api.services.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.crbt.api.services.domain.UploadType;
import com.crbt.api.services.repository.CategoryRepository;
import com.crbt.api.services.repository.UploadTypeRepository;
@Service
@Validated
public class UploadTypeServiceImpl implements UploadTypeService{

	private static final Logger logger = LoggerFactory.getLogger(UploadTypeServiceImpl.class);
	private final UploadTypeRepository repository;
	

	@Inject
	public UploadTypeServiceImpl(UploadTypeRepository repository) {
		super();
		this.repository = repository;
	}


	@Override
	public UploadType getDetailsByUploadId(Integer id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}
	
	
}
