package com.lps.ldtracker.model;

//import java.util.Date;

import lombok.Data;

@Data
public class ManageResourceRequest {
	

	private String first_name;
    
	private String last_name;

	private String middle_name;
    
	private String suffix;

	private String gender;

	private String email;

	private String careerStep;

	private String employeeId;

	private String region;

	private String role_name;

	private String team_name;

	private String status;
    
	private String skills;

	
	public String first_name() {
		return first_name;
	}
	public String last_name() {
		return last_name;
	}
	public String middle_name() {
		return middle_name;
	}
	public String suffix() {
		return suffix;
	}
	public String gender() {
		return gender;
	}
	public String email() {
		return email;
	}
	public String careerStep() {
		return careerStep;
	}
	public String employeeId() {
		return employeeId;
	}
	public String region() {
		return region;
	}
	public String role_name() {
		return role_name;
	}
	public String team_name() {
		return team_name;
	}
	public String status() {
		return status;
	}
	public String skills() {
		return skills;
	}
}
