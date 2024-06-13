package com.lps.ldtracker.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TrainingDetail {
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainingdtl")
		@SequenceGenerator(sequenceName = "trainingdtl_seq", allocationSize = 1, name = "trainingdtl")
		private String trainingId;
		private String trainingName;
		private String trainingTypeId;
		private Date startDate;
		private Date dueDate;
		private String prerequisite;
		private String description; 
		private String link;
		private boolean required;
		private boolean certification;
		private String duration;
		private String fee; 
		private boolean approval;
		private String certId;
		private boolean isActive;
		private boolean isDeleted;
		private String createdBy;
		private Date createdDate;
		private String updatedBy;
		private Date updatedDate;
		
	}

