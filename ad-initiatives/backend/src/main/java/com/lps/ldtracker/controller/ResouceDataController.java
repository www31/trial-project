package com.lps.ldtracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lps.ldtracker.dto.ResourceDataDto;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.service.ResourceDataService;
@RestController
@RequestMapping("/api/v1/reports")



@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
public class ResouceDataController {
	
	@Autowired
	ResourceDataService resourceDataService;
	
	@GetMapping(value="/getRecordsList")
	public ResponseEntity<Result> getRecordsList() {
		Result result = new Result();
		try {
			List<ResourceDataDto> resourceDtl =  this.resourceDataService.getRecordList();
			result.setData(resourceDtl);
			result.setStatus("SUCCESS");
		} catch (Exception e) {
			result.setStatus("FAILED");
			e.printStackTrace();
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
