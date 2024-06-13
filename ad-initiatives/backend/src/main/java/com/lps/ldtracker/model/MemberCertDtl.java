package com.lps.ldtracker.model;

import lombok.Data;

@Data
public class MemberCertDtl {
	
	private String memberId;
	private String certID;
	private String certName;
	private String docID;
	private String expiryDate;
	private String completionDate;
	
	private String duration;
	private Integer fee;
	private String currency;
	private String certLink;
	private Boolean isRenewable;
	
	private String progressID;
	private String trainingID;
	private String trainingName;
	private String statusName;
	private String approverID;
	private String startDT;
	private String dueDT;
	private String statusID;
	private Integer timeSpentInMinutes;
	private String timeSpent;
}
