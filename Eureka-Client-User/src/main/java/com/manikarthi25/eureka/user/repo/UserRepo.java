package com.manikarthi25.eureka.user.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.manikarthi25.eureka.user.entity.UserEO;

@Repository
public interface UserRepo extends CrudRepository<UserEO, Long> {

	UserEO findByEmail(String username);
	
}
