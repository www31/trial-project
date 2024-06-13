package com.lps.ldtracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lps.ldtracker.entity.Training_Dtl;


public interface TrainingRepository extends JpaRepository<Training_Dtl, Integer> {

	Optional<Training_Dtl> findById(Integer Id);

}
