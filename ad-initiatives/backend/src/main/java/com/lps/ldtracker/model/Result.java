package com.lps.ldtracker.model;

import java.util.List;

import lombok.Data;

@Data
public class Result {
	
	private String status;
	private String message;
	private List<LdTrackerError> errors;
	private Object data;
}
