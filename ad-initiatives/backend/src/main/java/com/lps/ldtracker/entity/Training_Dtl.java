package com.lps.ldtracker.entity;

import static com.lps.ldtracker.service.StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER;
import static com.lps.ldtracker.service.StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER;
import static org.hibernate.id.OptimizableGenerator.INCREMENT_PARAM;

import java.sql.Date;
import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "TRAINING_DTL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Training_Dtl {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRAINING_DTL")
    @GenericGenerator(
        name = "SEQ_TRAINING_DTL", 
        strategy = "com.lps.ldtracker.service.StringPrefixedSequenceIdGenerator", 
        parameters = {
            @Parameter(name = INCREMENT_PARAM, value = "1"),
            @Parameter(name = VALUE_PREFIX_PARAMETER, value = "01-2024"),
            @Parameter(name = NUMBER_FORMAT_PARAMETER, value = "%09d") })
	@Column(name = "TRAINING_ID")
	private String id;
	@Column(name = "TRAINING_NAME", nullable = false)
	private String trainingName;
	@Column(name = "TRAINING_TYPE", nullable = false)
	private String trTypeID;
	@Column(name = "PRODUCT_NAME", nullable = false)
	private String productName;
	@Column(name = "START_DATE")
	private Date startDate;
	@Column(name = "DUE_DATE")
	private Date dueDate;
	@Column(name = "PRE_REQ")
	private String preReq;
	@Column(name = "TRAINING_DESC", length = 500, nullable = false)
	private String description;
	@Column(name = "TRAINING_LINK", length = 500)
	private String link;
	@Column(name = "TRAINING_TAGS", length = 500)
	private String tags;
	@Column(name = "IS_REQUIRED")
    private Boolean required;
	@Column(name = "CERT_FLAG")
    private Boolean certFlag;
	@Column(name = "CERT_ID")
    private String certId;
	@Column(name = "TR_CONDITION")
    private Integer condition;
	@Column(name = "TR_CONDITION_VALUE")
    private String conditionValue;
	@Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;
	@Column(name = "IS_DELETED")
    private Boolean isDeleted;
	@Column(name = "CREATED_BY", length = 50)
    private String createdBy;
	@Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
	@Column(name = "UPDATED_BY", length = 50)
    private String updatedBy;
	@Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;
}
