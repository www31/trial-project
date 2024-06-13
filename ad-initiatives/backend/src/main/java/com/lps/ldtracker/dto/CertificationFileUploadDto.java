package com.lps.ldtracker.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CertificationFileUploadDto {
	@JsonProperty("id")
    private Long id;
	
	@JsonProperty("emp_id")
    private String empId;
	
	@JsonProperty("certification_name")
	private String certificationName;
	    
	@JsonProperty("certification_date")
	private Date certificationDate;
	
	@JsonProperty("owner")
	private String owner;

    @JsonProperty("extension_name")
    private String extensionName;

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("file_name_original")
    private String fileNameOriginal;

    @JsonProperty("full_path")
    private String fullPath;

    @JsonProperty("file_size")
    private Long fileSize;

    @JsonProperty("channel_code")
    private String channelCode;

    @JsonProperty("created_on")
    private LocalDateTime localDateTime;
}
