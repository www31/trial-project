package com.lps.ldtracker.dto;

import java.util.List;

import lombok.Data;

@Data
public class ManageResourceDto {
	
	private String memberId;
	private String membername;
	private Integer employeeNum;
	private String roleName;
	private String teamName;
	private String membertrainings;
	private String membercert;
	private List<String> certifications;
}
