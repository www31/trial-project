package com.lps.ldtracker.controller;

import static com.lps.ldtracker.constants.LdTrackerConstants.AUTH_SUCCESS;
import static com.lps.ldtracker.constants.LdTrackerConstants.ERROR;
import static com.lps.ldtracker.constants.LdTrackerConstants.SUCCESS;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lps.ldtracker.dto.ResourceDto;
import com.lps.ldtracker.dto.SetTrainingDto;
import com.lps.ldtracker.entity.CertificationFileUpload;
import com.lps.ldtracker.entity.SkillsDetail;
import com.lps.ldtracker.entity.UserDtl;
import com.lps.ldtracker.exception.AuthenticationFailedException;
import com.lps.ldtracker.model.AuthenticationResponse;
import com.lps.ldtracker.model.LoginRequest;
import com.lps.ldtracker.model.LoginResponse;
import com.lps.ldtracker.model.RegistrationRequest;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.repository.SetTrainingRepository;
import com.lps.ldtracker.service.AuthenticationService;
import com.lps.ldtracker.service.CertificationFileUploadService;
import com.lps.ldtracker.service.ResourceService;
import com.lps.ldtracker.service.SetTrainingService;
import com.lps.ldtracker.service.SkillsDetailService;
import com.lps.ldtracker.service.UserDtlService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	private final ResourceService resourceService;
	private final CertificationFileUploadService certificationFileUploadService;
	private final SkillsDetailService skillsDetailService;
	private final SetTrainingService setTrainingService;
	private final SetTrainingRepository trainingProgressRepository;
	
	private final UserDtlService userDtlService; 
 
	@PostMapping(value="/register") 
	public ResponseEntity<Result> registerUser(@RequestBody RegistrationRequest request, final HttpServletRequest httpRequest){
		Result result = this.userDtlService.registerUser(request);
		if(null != result.getErrors() && !result.getErrors().isEmpty()) {
			 return new ResponseEntity<>(result, BAD_REQUEST);
		}
		return  new ResponseEntity<>(result, OK);
		
	}
	
	@PostMapping(value="/login")
	public ResponseEntity<Result> login(
		@RequestBody LoginRequest loginRequest
	) throws JsonProcessingException {
		Result result = new Result();
		String userName = loginRequest.getUsername();
		Optional<UserDtl> userDtl = this.userDtlService.findByUserName(userName);
		try {
			AuthenticationResponse authResponse = authenticationService.login(loginRequest);
			if(userDtl.isPresent()) {
				UserDtl userDtl2 = userDtl.get(); 
				var loginData = LoginResponse.builder()
						.userId(userDtl2.getUserId())
						.isActive(userDtl2.getIsActive())
						.userName(userDtl2.getUsername())
						.userPass(userDtl2.getUserPass())
						.isDeleted(userDtl2.getIsDeleted())
						.token(authResponse.getToken())
						.createdBy(userDtl2.getCreatedBy())
						.createdDate(userDtl2.getCreatedDate())
						.updatedBy(userDtl2.getUpdatedBy())
						.updatedDate(userDtl2.getUpdatedDate())
						.accessName(userDtl2.getAccessLevel().getAlName().toUpperCase())
						.build();
				result.setData(loginData);
				result.setMessage(AUTH_SUCCESS);
				result.setStatus(SUCCESS);
			}
			return ResponseEntity
					.ok(result);
		} catch (AuthenticationFailedException authenticationFailedException) {
			result.setData(null);
			result.setMessage(authenticationFailedException.getMessage());
			result.setStatus(ERROR);
			return ResponseEntity
				.status(FORBIDDEN)
				.body(result);
		}
	}
	
	@PostMapping(value="/reset") 
	public ResponseEntity<Result> resetPassword(@RequestBody RegistrationRequest request, final HttpServletRequest httpRequest){
		Result result = this.userDtlService.resetPassword(request);
		if(null != result.getErrors() && !result.getErrors().isEmpty()) {
			 return new ResponseEntity<>(result, BAD_REQUEST);
		}
		return  new ResponseEntity<>(result, OK);
		
	}
	
	@PostMapping(value="/exist-username")
	public ResponseEntity<Result> existingUsername(@RequestBody LoginRequest request) {
		Result result = this.userDtlService.isExistUsername(request);
		if(null != result.getErrors() && !result.getErrors().isEmpty()) {
			 return new ResponseEntity<>(result, BAD_REQUEST);
		}
		return  new ResponseEntity<>(result, OK);
	}
	
	@GetMapping
	public String confirmUserAccount(@RequestParam("verify") String token) {
		String result = this.userDtlService.verifyToken(token);
		return result;
	}
	
	@PostMapping(value="/refresh-token")
	public String getRefreshToken(@RequestBody String request) {
		return this.userDtlService.refreshToken(request);
	}
	
	@PostMapping(value="/addResource")
	public ResponseEntity<Result> addUser(@RequestBody ResourceDto resourceDto, final HttpServletRequest httpRequest) {

		return new ResponseEntity<Result>(resourceService.addResource(resourceDto), HttpStatus.OK); 
	}
	
	@PostMapping(value="/resourceCertificationUpload")
	public ResponseEntity<Object> certificationUpload(@RequestParam("files") MultipartFile[] files,
            												@RequestParam Map<String, String> headers) {
		return ResponseEntity.ok().body(this.certificationFileUploadService.uploadFiles(files, headers));
	}
	
	@GetMapping(value="/viewResource/{id}")
	public ResponseEntity<Object> viewResource(@PathVariable String id) {
		Result result = this.resourceService.viewResource(id);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(value="/viewResourceCertification/{ownerId}")
	public ResponseEntity<Object> viewResourceCertification(@PathVariable String ownerId) {
		Result result = this.certificationFileUploadService.viewResourceCertification(ownerId);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping(value="/editResource/{id}")
    public ResponseEntity<Result> editResource(@PathVariable String id, @RequestBody ResourceDto resourceDto) {
        Result result = resourceService.editResource(id, resourceDto);
        return new ResponseEntity<>(result, result.getStatus().equals("SUCCESS") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
	
	@GetMapping("/getResources")
    public ResponseEntity<List<ResourceDto>> getAllResources() {
        List<ResourceDto> resources = resourceService.getAllResources();
        return ResponseEntity.ok(resources);
    }
	
	@GetMapping("/getSkills")
    public ResponseEntity<List<SkillsDetail>> getAllSkillDetails() {
		 List<SkillsDetail> skillDetails = skillsDetailService.getAllSkillDetails();
	        return new ResponseEntity<>(skillDetails, HttpStatus.OK);
    }
	
	@PutMapping(value="/resourceCertificationUpdate")
	public ResponseEntity<Object> updateCertifications(@RequestParam("files") MultipartFile[] files,
             @RequestParam Map<String, String> headers) {
		return ResponseEntity.ok().body(certificationFileUploadService.updateCertifications(files, headers));
	}
	
	@PostMapping("/setTraining")
    public ResponseEntity<Result> assignTrainings(@RequestBody SetTrainingDto request) {
		Result result =  setTrainingService.assignTrainings(request.getMemberIds(), request.getTrainings());
		return new ResponseEntity<>(result, HttpStatus.OK);
    }
	
	@GetMapping("/countTrainings/{memberId}")
    public int getNumberOfTrainingsAssigned(@PathVariable String memberId) {
        return trainingProgressRepository.countByMemberId(memberId);
    }
	
}