package com.lps.ldtracker.model;

import lombok.Data;

@Data
public class MemberInfo {
	
	private String memberId;
	private String lastName;
	private String firstName;
	private String middleName;
	private String fullName;
	private Integer employeeNum;
	private String regionId;
	private String emailAddress;
	private String employmentDt;
	private String clCode;
	private String teamName;
	private String empStatus;
	private String roleName;
	private String pmId;
	private String supervisorId;
	private String pmName;
	private String supervisorName;
	
}
