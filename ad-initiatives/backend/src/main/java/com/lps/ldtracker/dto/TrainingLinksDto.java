package com.lps.ldtracker.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class TrainingLinksDto {	

	private Integer subTrId;
	private String subTrName;
	private String subTrLink;
	private String trainingId;
	private boolean isActive;
	private boolean isDeleted;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
    
}
