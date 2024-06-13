package com.lps.ldtracker.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lps.ldtracker.constants.LdTrackerConstants;
import com.lps.ldtracker.dto.CertificationFileUploadDto;
import com.lps.ldtracker.entity.CertificationFileUpload;
import com.lps.ldtracker.entity.Resource;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.repository.CertificationFileUploadRepository;
import com.lps.ldtracker.repository.ResourceRepository;
import com.lps.ldtracker.serviceImpl.ResourceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CertificationFileUploadService {
	private static final Logger logger =   LoggerFactory.getLogger(CertificationFileUploadService.class);
	private final CertificationFileUploadRepository certificationFileUploadRepository;
	private ResourceRepository resourceRepository;
	
	private final ModelMapper modelMapper;
	
	@Value("${upload.files.extensions}")
    private String fileExtensions;
	@Value("${upload.files.path}")
	private String uploadPath;
	
	public List<CertificationFileUploadDto> uploadFiles(MultipartFile[] uploadingFiles, Map<String, String> headers) {
		AtomicInteger index = new AtomicInteger(0);
		if (uploadingFiles == null) {
			log.error("Invalid file upload");
			throw new IllegalArgumentException("Invalid file");
		}
		System.out.println("Headers: " + headers);
		for (int i = 0; i < uploadingFiles.length; i++) {
		      String nameKey = "name_" + i; // Construct key for name
		      String calendarKey = "calendar_" + i; // Construct key for calendar

		      String name = headers.get(nameKey); // Extract the name
		      String calendar = headers.get(calendarKey); // Extract the calendar

		      System.out.println("Name for item " + i + ": " + name);
		      System.out.println("Calendar for item " + i + ": " + calendar);

		      MultipartFile file = uploadingFiles[i]; // Access the corresponding file
		      if (file != null) {
		        System.out.println("File " + i + ": " + file.getOriginalFilename());
		      }
		}
		List<CertificationFileUpload> certificationFileUploads = new ArrayList<>();
		
		Arrays.stream(uploadingFiles).forEach(file -> {
			int currentIndex = index.getAndIncrement();
			try {
		
				  String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
				  String fileExtension = getFileExtension(filename);
				  
				  //Keys
				  String nameKey = "name_" + currentIndex;
				  String calendarKey = "calendar_" + currentIndex;
				  String certificationOwnerKey = "owner";
				  
				  //Get value of keys
			      String certificationName = headers.get(nameKey);	      
			      String certificationCalendar = headers.get(calendarKey);
			      String certificationOwner = headers.get(certificationOwnerKey);
			      
			      
			      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			      Date newCertificationCalendar = dateFormat.parse(certificationCalendar);
			      System.out.println("Name for item " + currentIndex + ": " + certificationName);
			      System.out.println("Calendar for item " + currentIndex + ": " + certificationCalendar);
					
				  if (!fileExtensions.contains(fileExtension)) {
						log.error("Invalid File Upload");
						throw new IllegalArgumentException("Invalid file extension");
					}
					
				  byte[] fileContent = file.getBytes();
				  Files.write(Paths.get(uploadPath + file.getOriginalFilename()), fileContent);
					
				  String fileNameUpload = FilenameUtils.removeExtension(filename) + "_" + Calendar.getInstance().getTimeInMillis() + "." + getFileExtension(filename);
					
				  CertificationFileUpload certificationFileUpload = new CertificationFileUpload();
				  certificationFileUpload.setCertificationName(certificationName);
				  certificationFileUpload.setCertificationDate(newCertificationCalendar);
				  certificationFileUpload.setOwner(certificationOwner);
				  certificationFileUpload.setExtensionName(getFileExtension(filename));
				  certificationFileUpload.setStatus(true);
				  certificationFileUpload.setFileName(fileNameUpload);
				  certificationFileUpload.setFileNameOriginal(FilenameUtils.removeExtension(filename));
				  certificationFileUpload.setFullPath(uploadPath + filename);
				  certificationFileUpload.setFileSize(file.getSize());
				  certificationFileUpload.setChannelCode(headers.get("channelCode"));
				  certificationFileUpload.setFileContent(fileContent);
				  certificationFileUploads.add(certificationFileUpload);	
				
				} catch(IOException e) {
					log.error("Upload file error {}", e.getLocalizedMessage());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			
		});
		
		certificationFileUploadRepository.saveAll(certificationFileUploads);
		
		return certificationFileUploads.stream()
                .map(m -> modelMapper.map(m, CertificationFileUploadDto.class))
                .collect(Collectors.toList());
	}
	
	public String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex < 0) {
            return null;
        }
        return fileName.substring(dotIndex + 1);
	}
	

	public Result viewResourceCertification(String ownerId) {
		try {
			logger.debug("ownerId: " + ownerId);
			Result result = new Result();
			List<CertificationFileUpload> certifications = certificationFileUploadRepository.findByOwner(ownerId); 
			
			if (certifications == null || certifications.isEmpty()) {
	            result.setMessage("No certifications found for user with ID: " + ownerId);
	            result.setStatus("ERROR");
	            return result;
	        }
			result.setMessage(LdTrackerConstants.SUCCESS);
			result.setStatus(LdTrackerConstants.SUCCESS);
			result.setData(certifications);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR view resource: " + e.getMessage());
			throw e;
		}
		
	}
	
	
	public List<CertificationFileUploadDto> updateCertifications(MultipartFile[] uploadingFiles, Map<String, String> headers) {
	    if (uploadingFiles == null) {
	        log.error("Invalid file upload");
	        throw new IllegalArgumentException("Invalid file");
	    }

	    List<CertificationFileUploadDto> updatedFiles = new ArrayList<>();
	    
	    for (int i = 0; i < uploadingFiles.length; i++) {
		      String nameKey = "name_" + i; // Construct key for name
		      String calendarKey = "calendar_" + i; // Construct key for calendar
		      String ownerKey = "owner";
		      String idKey= "id_" + i;

		      String name = headers.get(nameKey); // Extract the name
		      String calendar = headers.get(calendarKey); // Extract the calendar
		      String certificationOwner = headers.get(ownerKey);
		      String certificationId = headers.get(idKey);

		      System.out.println("Name for item " + i + ": " + name);
		      System.out.println("Calendar for item " + i + ": " + calendar);
		      System.out.println("Owner for item " + i + ": " + certificationOwner);
		      System.out.println("certificationId for item " + i + ": " + certificationId);

		      MultipartFile file = uploadingFiles[i]; // Access the corresponding file
		      if (file != null) {
		        System.out.println("File " + i + ": " + file.getOriginalFilename());
		      }
		}

	    for (int i = 0; i < uploadingFiles.length; i++) {
	        try {
	            String nameKey = "name_" + i;
	            String calendarKey = "calendar_" + i;
	            String ownerKey = "owner";
	            String idKey= "id_" + i;

	            String certificationName = headers.get(nameKey);
	            String certificationCalendar = headers.get(calendarKey);
	            String certificationOwner = headers.get(ownerKey);
	            String certificationId = headers.get(idKey);
	            Long certificationIdInt = Long.parseLong(certificationId);

	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            Date newCertificationCalendar = dateFormat.parse(certificationCalendar);

	            MultipartFile file = uploadingFiles[i];
	            if (file != null) {
	                String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
	                String fileExtension = getFileExtension(filename);

	                Optional <CertificationFileUpload> existingFile = Optional.of(certificationFileUploadRepository.findByFileName(certificationName));
	                System.out.println("File existingFile" + i + ": " + existingFile);
	                System.out.println("File certificationIdInt" + i + ": " + certificationIdInt);
	                if (existingFile.isPresent()) {
	                    if (!fileExtensions.contains(fileExtension)) {
	                        log.error("Invalid File Upload");
	                        throw new IllegalArgumentException("Invalid file extension");
	                    }
	                    CertificationFileUpload _existingFile = existingFile.get();

	                    byte[] fileContent = file.getBytes();
	                    Files.write(Paths.get(_existingFile.getFullPath()), fileContent);
	                    System.out.println("File Size" + i + ": " + file.getSize());

	                    _existingFile.setCertificationName(certificationName);
	                    _existingFile.setCertificationDate(newCertificationCalendar);
	                    _existingFile.setOwner(certificationOwner);
	                    _existingFile.setFileSize(file.getSize());
	                    _existingFile.setFileContent(fileContent);

	                    certificationFileUploadRepository.save(_existingFile);

	                    updatedFiles.add(modelMapper.map(existingFile, CertificationFileUploadDto.class));
	                } else {
	                    log.error("File not found: {}", filename);
	                }
	            }
	        } catch (IOException e) {
	            log.error("Upload file error: {}", e.getMessage());
	        } catch (ParseException e) {
	            log.error("Date parsing error: {}", e.getMessage());
	        }
	    }

	    return updatedFiles;
	}
}
