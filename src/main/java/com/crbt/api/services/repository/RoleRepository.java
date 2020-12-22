package com.crbt.api.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crbt.api.services.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
