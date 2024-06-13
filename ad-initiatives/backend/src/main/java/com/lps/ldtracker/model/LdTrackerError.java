package com.lps.ldtracker.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LdTrackerError {
	private String code;
	private String message;
	private LocalDateTime timestamp;
	
	public LdTrackerError (String code, String message) {
		this.code = code;
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}
}
