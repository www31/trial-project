package com.lps.ldtracker.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.lps.ldtracker.service.StringPrefixedSequenceIdGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "STATUS_DTL")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusDetail {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STATUS_DTL")
    @GenericGenerator(
        name = "SEQ_STATUS_DTL", 
        strategy = "com.lps.ldtracker.service.StringPrefixedSequenceIdGenerator", 
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "06-2024"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%09d") })
	@Column(name  = "STATUS_ID", length = 36)
	private String statusId;
	@Column(name = "IS_ACTIVE")
	private Boolean isActive;
	@Column(name = "STATUS_NAME", nullable = false)
	private String statusName;
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	@Column(name = "TYPE", length = 20)
	private String type;
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
