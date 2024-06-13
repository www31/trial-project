package com.lps.ldtracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lps.ldtracker.entity.UserDtl;


public interface UserDtlRepository extends JpaRepository<UserDtl, String> {
	Optional<UserDtl> findByUserName(String username);
	UserDtl findByUserNameIgnoreCase(String username);
	Boolean existsByUserName(String username);
}
