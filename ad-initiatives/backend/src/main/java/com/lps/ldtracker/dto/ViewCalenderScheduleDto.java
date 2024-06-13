package com.lps.ldtracker.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ViewCalenderScheduleDto {
	private String trainingName;
	private String trainingDesc;
	private Date startDate;
	private Date dueDate;
	private String trainingId;
}
