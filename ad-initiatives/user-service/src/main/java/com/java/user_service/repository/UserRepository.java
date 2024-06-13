package com.java.user_service.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.java.user_service.entity.User;

public interface UserRepository extends CrudRepository<User, String> {

	List<User> findAll();

	boolean existsByEmail(String email);
}
