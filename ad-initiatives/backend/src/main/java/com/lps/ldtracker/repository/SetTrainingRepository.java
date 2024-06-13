package com.lps.ldtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lps.ldtracker.entity.SetTraining;

public interface SetTrainingRepository extends JpaRepository<SetTraining, String>{
	SetTraining findTopByOrderByProgressIdDesc();
	int countByMemberId(String memberId);
}
