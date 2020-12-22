package com.crbt.api.services.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.crbt.api.services.domain.UploadType;

public interface UploadTypeService {
	
	UploadType getDetailsByUploadId(@Valid @NotNull final Integer id);

}
