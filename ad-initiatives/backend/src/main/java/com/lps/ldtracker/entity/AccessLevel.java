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

@Entity 
@Table(name = "ACCESS_LEVEL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessLevel {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ACCESS_DTL")
    @GenericGenerator(
        name = "SEQ_ACCESS_DTL", 
        strategy = "com.lps.ldtracker.service.StringPrefixedSequenceIdGenerator", 
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "09-2024"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%09d") })
	@Column(name = "AL_ID", length = 100)
	private String alId;
	@Column(name = "AL_NAME", length = 100)
	private String alName;
	@Column(name = "AL_DESC", length = 100)
	private String alDesc;
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
