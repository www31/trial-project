package com.lps.ldtracker.model;

import lombok.Data;

@Data
public class ViewTrainingDetail {
	
	private String id;
	private String name;
	private String status;
	private String duration;
	private Integer progressNumber;
	private String date_started;
	private String date_completed;
	private Integer estimated_hours;
	private String due_date;
	private String target_date;
	private String link;
	private String remarks;
	private String description;
	
}
