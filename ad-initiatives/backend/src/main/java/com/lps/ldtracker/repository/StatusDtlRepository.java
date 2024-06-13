package com.lps.ldtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lps.ldtracker.entity.StatusDetail;

public interface StatusDtlRepository extends  JpaRepository<StatusDetail, String>{

}