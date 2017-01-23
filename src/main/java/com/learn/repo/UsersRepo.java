package com.learn.repo;

import java.util.Optional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.repository.CrudRepository;

import com.learn.store.UsersEntity;



/*
 *  @version     1.0, 02-Jan-2017
 *  @author gaurav
*/

public interface UsersRepo extends CrudRepository<UsersEntity, Long> {
    
    public UsersEntity findByMobileNumber(String mobileNumber);
    
}
