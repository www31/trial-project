package com.lps.ldtracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lps.ldtracker.dto.ManageResourceDto;
import com.lps.ldtracker.model.CertDetail;
//import com.lps.ldtracker.model.Resource_Dtl;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.model.UserDetail;
import com.lps.ldtracker.model.ViewTrainingDetail;
import com.lps.ldtracker.service.ManageResourcesService;


@RestController
@RequestMapping("/api/v1/resources")
public class ManageResourcesController {
	
	@Autowired
	ManageResourcesService manageResourcesService;

	@GetMapping(value="/getResources")
	public ResponseEntity<Result> getResources() {
		Result result = new Result();
		try {
			List<ManageResourceDto> resourceDtl =  this.manageResourcesService.getResourceList();
			result.setData(resourceDtl);
			result.setStatus("SUCCESS");
		} catch (Exception e) {
			result.setStatus("FAILED");
			e.printStackTrace();
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
//	@GetMapping(value="/getResourceList")
//	public ResponseEntity<Result> getResourceList() {
//		List<Training_Dtl> usr = this.manageResourcesService.getResourceList();
//		Result result = new Result();
//		result.setData(usr);
//		
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
	
	@GetMapping(value="/getViewTrainingDtl")
	public ResponseEntity<Result> getViewTrainingDtl() {
		Result result = new Result();
		try {
			List<ViewTrainingDetail> viewTrainingDtl = this.manageResourcesService.getAllViewTrainingDetails();
			result.setData(viewTrainingDtl);
			result.setStatus("SUCCESS");
		} catch (Exception e) {
			result.setStatus("FAILED");
			e.printStackTrace();
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(value="/getCertList/")
	public ResponseEntity<Result> getCertList() {
		Result result = new Result();
		try {
		List<CertDetail> viewCertList  = this.manageResourcesService.getCertDetailList();
		result.setData(viewCertList);
		result.setStatus("SUCCESS");
	} catch (Exception e) {
		result.setStatus("FAILED");
		e.printStackTrace();
	}
	return new ResponseEntity<>(result, HttpStatus.OK);
}
	
	@GetMapping(value="/getProfileInfo/{memberId}")
	public ResponseEntity<Result> getProfileInformation(@PathVariable String memberId ) {
		List<UserDetail> usr  = this.manageResourcesService.getUserById(memberId);
		Result result = new Result();
		result.setData(usr);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	
	}	
	
	@GetMapping(value="/getCertPerCertName/")
	public ResponseEntity<Result> getCertPerCertName() {
		Result result = new Result();
		try {
		List<CertDetail> viewCertListPerName  = this.manageResourcesService.getCertPerCertName();
		result.setData(viewCertListPerName);
		result.setStatus("SUCCESS");
	} catch (Exception e) {
		result.setStatus("FAILED");
		e.printStackTrace();
	}
	return new ResponseEntity<>(result, HttpStatus.OK);
}	
	@GetMapping(value="/getCertPerTeam/")
	public ResponseEntity<Result> getCertPerTeam() {
		Result result = new Result();
		try {
		List<CertDetail> viewCertListPerTeam  = this.manageResourcesService.getCertPerTeam();
		result.setData(viewCertListPerTeam);
		result.setStatus("SUCCESS");
	} catch (Exception e) {
		result.setStatus("FAILED");
		e.printStackTrace();
	}
	return new ResponseEntity<>(result, HttpStatus.OK);
}		
	
}
