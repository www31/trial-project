package com.lps.ldtracker.entity;

import java.util.Date;

import com.lps.ldtracker.security.RoleSecurity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRAINING_PROGRESS")
@NoArgsConstructor
@AllArgsConstructor 
@Data
public class SetTraining {
	@Id
	@Column(name = "PROGRESS_ID")
    private String progressId;

    @Column(name = "IS_REQUIRED")
    private boolean isRequired;

    @Column(name = "DUE_DT")
    private Date dueDate;

    @Column(name = "TRAINING_DT")
    private String trainingId;

    @Column(name = "MEMBER_ID")
    private String memberId;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;
}
