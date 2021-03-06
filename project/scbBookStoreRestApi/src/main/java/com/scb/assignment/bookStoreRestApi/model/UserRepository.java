package com.scb.assignment.bookStoreRestApi.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByToken(@Param("token") String token);

	User findByUsername(@Param("username") String username);
}
