package com.lps.ldtracker.dto;

import java.util.Date;

import com.lps.ldtracker.security.RoleSecurity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrainingDto {
	 private String label;
	 private String value;
	 private Date date;
}
