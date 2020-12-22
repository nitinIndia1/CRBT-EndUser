package com.crbt.api.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crbt.api.services.domain.UploadType;

public interface UploadTypeRepository extends JpaRepository<UploadType, Integer>{

}
