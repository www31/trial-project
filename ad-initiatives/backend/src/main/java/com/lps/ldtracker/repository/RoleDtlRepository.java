package com.lps.ldtracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lps.ldtracker.entity.RoleDtl;

public interface RoleDtlRepository extends JpaRepository<RoleDtl, String>{
	Optional<RoleDtl> findByRoleName(String roleName);
}