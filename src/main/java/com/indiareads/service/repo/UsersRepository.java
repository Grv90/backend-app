package com.indiareads.service.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.indiareads.service.domain.UserEntity;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {
    
	UserEntity findByMobileNumber(String mobileNumber);
	
	//@Query(value="SELECT * FROM users where id = ", nativeQuery=true)
	UserEntity findById(Long id);

	@Query(value="SELECT * FROM users where mobile_number = ?1 OR email_address = ?2", nativeQuery=true)
	UserEntity findByMobileNumberOrEmailAddress(String mobileNumber, String emailAddress);

	@Query(value="SELECT * FROM users where email_address = ?1 OR mobile_number = ?1 AND password = ?2 AND is_active = ?3", nativeQuery=true)
	UserEntity findOneByEmailAddressOrMobileNumberAndPassword(String username, String password, Boolean isActive);

}
