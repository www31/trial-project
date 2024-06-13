package com.lps.ldtracker.entity;

import java.time.LocalDateTime;
import java.util.Date;

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
@Table(name = "MEMBER_DTL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDetail {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEMBER_DTL")
    @GenericGenerator(
        name = "SEQ_MEMBER_DTL", 
        strategy = "com.lps.ldtracker.service.StringPrefixedSequenceIdGenerator", 
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "LPS2024"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%09d") })
	@Column(name = "MEMBER_ID", length = 36)
	private String memberDetailId;
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	@Column(name = "EMPLOYEE_NUM", nullable = false)
	private Integer employeeNum;
	@Column(name = "EMAIL_ADDRESS", length = 100, nullable = false)
	private String emailAddress;
	@Column(name = "IS_DELETED")
	private Boolean isDeleted;
	@Column(name = "EMPLOYMENT_DT")
	private Date employmentDt;
	@Column(name = "REGION_ID", length = 15)
	private String regionId;
	@Column(name = "CAREER_LEVEL_ID", length = 36, nullable = false)
	private String careerLevelId;
	@Column(name = "TEAM_ID", length = 36, nullable = false)
	private String teamId;
	@Column(name = "STATUS_ID", length = 36, nullable = false)
	private String statusId;
	@Column(name = "CREATED_BY", length = 36)
	private String createdBy;
	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;
	@Column(name = "UPDATED_BY", length = 36)
	private String updatedBy;
	@Column(name = "UPDATED_DATE")
	private LocalDateTime updatedDate;
}
