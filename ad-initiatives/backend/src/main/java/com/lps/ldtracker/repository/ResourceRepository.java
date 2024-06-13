package com.lps.ldtracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lps.ldtracker.entity.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long>{
	Optional<Resource> findByEmailAddress(String email);
	Optional<Resource> findTopByOrderByMemberIdDesc();
	Optional<Resource> findByMemberId(String id);
 }
