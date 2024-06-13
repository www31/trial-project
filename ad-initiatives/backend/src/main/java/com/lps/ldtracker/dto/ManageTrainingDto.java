package com.lps.ldtracker.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ManageTrainingDto extends TrainingLinksDto{	

	private String Id;	
	private String trainingName;
	private String trainingType;
	private String productName;
	private Date startDate;
	private Date dueDate;
	private String preReq;
	private String description;
	private String trainingTags;  
    private Boolean isRequired;
    private Boolean certification;
    private Integer trCondition;
    private String trConditionValue;
    private boolean active;
    
    private String certID;
    private String certName;
    private String duration;
    private Integer fee;
    private String currency;
    private String certLink;
    private Date expiryDate;
    private Boolean renewable;
    private String skillID;
    private String createdBy;
    
    private List <TrainingLinksDto> trainingLinksLists = new ArrayList<>();
    

}
