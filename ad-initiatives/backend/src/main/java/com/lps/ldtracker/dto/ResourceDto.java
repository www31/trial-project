package com.lps.ldtracker.dto;

import java.util.List;

import com.lps.ldtracker.entity.CertificationFileUpload;
import com.lps.ldtracker.security.RoleSecurity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceDto {
	
	private String memberId;
	private String firstname;
	private String lastname;
	private String middlename;
	private String suffix;
	private String gender;
	private String empId;
	private String emailAddress;
	private String region;
	private Boolean isEnabled = false;
	private String careerStep;
	private String team;
	private String status;
	private String password;
	@Enumerated(EnumType.STRING)
	private RoleSecurity role;
	private String skills;
//	private List<CertificationFileUpload> certifications;
}
