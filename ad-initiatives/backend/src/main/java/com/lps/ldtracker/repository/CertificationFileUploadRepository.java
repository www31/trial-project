package com.lps.ldtracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lps.ldtracker.entity.CertificationFileUpload;
import com.lps.ldtracker.entity.Resource;

@Repository
public interface CertificationFileUploadRepository extends JpaRepository<CertificationFileUpload, Long>{
	List<CertificationFileUpload> findByOwner(String owner);
	CertificationFileUpload findByFileName(String fileName);
}
