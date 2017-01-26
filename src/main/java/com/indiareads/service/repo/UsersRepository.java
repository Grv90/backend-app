package com.indiareads.service.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.indiareads.service.domain.UserEntity;
/*
 *  @version     1.0, 02-Jan-2017
 *  @author gaurav
 */

public interface UsersRepository extends CrudRepository<UserEntity, Long> {

	public UserEntity findByMobileNumber(String mobileNumber);

	@Query(value="SELECT * FROM users where email_address = ?1 OR mobile_number = ?1 AND password = ?2 AND is_active = ?3", nativeQuery=true)
	public UserEntity findOneByEmailAddressOrMobileNumberAndPassword(String username, String password, Boolean isActive);

}
