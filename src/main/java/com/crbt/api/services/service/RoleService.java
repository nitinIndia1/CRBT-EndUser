package com.crbt.api.services.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crbt.api.services.domain.Role;


public interface RoleService {

	Role update(@Valid @NotNull final Role role);

	Role save(@Valid @NotNull final Role role);

	Page<Role> listAllRole(Pageable page);

}
