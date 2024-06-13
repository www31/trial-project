package com.lps.ldtracker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
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

@Data
@Entity
@Table(name = "ROLE_DTL")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDtl {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roledtl")
	@SequenceGenerator(sequenceName = "SEQ_ROLE_DTL", allocationSize = 1, name = "roledtl")
	@Column(name = "ROLE_ID")
	private String roleId;
	@Column(name = "ACTIVE")
	private Boolean active;
	@Column(name = "ROLE_NAME", nullable = false)
	private String roleName;
	@Column(name = "ROLE_DESC")
	private String roleDesc;
	@Column(name = "IS_DELETED")
	private Boolean isDeleted;
	@Column(name = "CREATED_BY", length = 36)
	private String createdBy;
	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;
	@Column(name = "UPDATED_BY", length = 36)
	private String updatedBy;
	@Column(name = "UPDATED_DATE")
	private LocalDateTime updatedDate;
}
