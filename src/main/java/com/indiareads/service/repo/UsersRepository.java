package com.indiareads.service.repo;

import org.springframework.data.repository.CrudRepository;

import com.indiareads.service.domain.UserEntity;
/*
 *  @version     1.0, 02-Jan-2017
 *  @author gaurav
 */

public interface UsersRepository extends CrudRepository<UserEntity, Long> {

	public UserEntity findByMobileNumber(String mobileNumber);

}
