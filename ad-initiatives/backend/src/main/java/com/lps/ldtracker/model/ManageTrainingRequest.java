package com.lps.ldtracker.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ManageTrainingRequest {
	public ManageTrainingRequest(BigDecimal processId1, BigDecimal trainingId1, BigDecimal duration1,
			java.sql.Date startDt1, int fee1, String trainingLink1, String trainingName1, Boolean type1,
			java.sql.Date dueDt1, String roleName1, String typeCert1, String certLink1) {
		// TODO Auto-generated constructor stub
	}
	private BigDecimal processId;
	private BigDecimal trainingId;
	private BigDecimal duration;
	private Date startDt;
	private int fee;
	private String trainingLink;
	private String trainingName;
	private Boolean type;
	private Date dueDt;
	private String roleName;
	private String typeCert;	
	private String certLink;	

	
}
