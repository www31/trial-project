package com.lps.ldtracker.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lps.ldtracker.entity.SkillsDetail;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.service.SkillsDetailService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/skills")
public class ManageSkillsController {
	
	@Autowired
	SkillsDetailService skillsDetailService;
	
	
	@GetMapping(value="/getSkillDetails")
	public ResponseEntity<Result> getSkillDetails() {
		Result result = new Result();
		try {
			List<SkillsDetail> skillsDtl =  this.skillsDetailService.getSkillDetails();
			result.setData(skillsDtl);
			result.setStatus("SUCCESS");
		} catch (Exception e) {
			result.setStatus("FAILED");
			e.printStackTrace();
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
