package com.java.user_service.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "suffix")
	private String suffix;

	@Column(name = "email_address")
	private String email;

	@Column(name = "employee_id")
	private String employeeId;

	@Column(name = "career_step_id")
	private String careerStep;

	@Column(name = "location")
	private String location;

	@Column(name = "project_id")
	private String projectId;

	@Column(name = "team_id")
	private String teamId;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "supervisor_id")
	private User supervisor;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id")
	private User manager;

	@Column(name = "image")
	private String image;

	@Column(name = "active")
	private boolean active;

	@Column(name = "created_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private String createdDate;

	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private String lastUpdatedDate;
}
