package com.crbt.api.services.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crbt.api.services.domain.User;

public interface UserService {

	Page<User> listAllUser(Pageable page);

	User save(@Valid @NotNull final User user);

	User update(@Valid @NotNull final User user);
	
	User getUserById(@Valid @NotNull final Integer id);

}
