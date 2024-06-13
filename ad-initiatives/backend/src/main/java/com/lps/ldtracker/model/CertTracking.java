package com.lps.ldtracker.model;

import lombok.Data;
import java.sql.Date;

@Data
public class CertTracking {
	
	private String certification_name;
	private String skill;
	private String requester;
	private String status;
	private String certification_details;
	private Date training_completion_date;

}