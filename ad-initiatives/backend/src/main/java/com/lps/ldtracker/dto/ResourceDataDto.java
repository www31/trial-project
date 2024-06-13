package com.lps.ldtracker.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ResourceDataDto {
	
	private String memberId;
	private String memberName;
	private Integer employeeNum;
	private String roleName;
	private String teamName;
	private String careerStep;	
	private String isActive;
	private String productName;
	private String certName;
	private String certLink;
	private Date expiryDate;
	private Date startDate;
	private Date dueDate;
	private String status;
	private String certId;
	private String trainingId;
	private boolean certFlag;
	private String forCertification;
	private String upcomingCertication;	
	private String delayed;
	private String ongoingTrainings;
	private String progressId;
	
	

}
