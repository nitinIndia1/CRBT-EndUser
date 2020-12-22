package com.crbt.api.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crbt.api.services.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	

}
