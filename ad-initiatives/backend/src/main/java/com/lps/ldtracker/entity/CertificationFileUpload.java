package com.lps.ldtracker.entity;

import java.io.Serializable;
import java.util.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "certification_file_dtl")
@NoArgsConstructor
@AllArgsConstructor 
@Data

public class CertificationFileUpload implements Serializable{
	 private static final long serialVersionUID = -9082333384269286004L;

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Long id;
	    
	    @Column(name= "certification_name")
	    private String certificationName;
	    
	    @Column(name= "certification_date")
	    private Date certificationDate;
	    
	    @Column(name= "owner")
	    private String owner;
	    
	    @Column(name = "file_name")
	    private String fileName;

	    @Column(name = "extension_name")
	    private String extensionName;

	    @Column(name = "file_name_original")
	    private String fileNameOriginal;

	    @Column(name = "file_size")
	    private Long fileSize;

	    @Column(name = "full_path")
	    private String fullPath;

	    @Column(name = "channel_code")
	    private String channelCode;

	    private boolean status;

	    @Column(name = "created_date")
	    private LocalDateTime localDateTime = LocalDateTime.now();
	    
	    @Column(name = "file_content")
	    @Lob
	    private byte[] fileContent;
}
