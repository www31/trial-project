package com.lps.ldtracker.dto;

import lombok.Data;

@Data
public class ManageResourceSkillSetDto {
	private String skillSetId;
	private String type;
	private String memberId;
	private String trainingId;
	private String skillDtlId;
	private boolean isDeleted=false;
	private String skillDetailId;
	private String skillName;
	private String description;
	private boolean isActive=false;
}
