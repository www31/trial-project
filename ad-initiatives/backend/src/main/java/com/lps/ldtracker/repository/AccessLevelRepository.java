package com.lps.ldtracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lps.ldtracker.entity.AccessLevel;

public interface AccessLevelRepository extends JpaRepository<AccessLevel, String> {
	Optional<AccessLevel> findByAlName(String name);
}