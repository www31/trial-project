package com.lps.ldtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lps.ldtracker.entity.ConfirmationDetail;

@Repository
public interface ConfirmationRepository extends JpaRepository<ConfirmationDetail, Long> {
	ConfirmationDetail findByToken(String token);
}
