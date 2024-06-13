package com.lps.ldtracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lps.ldtracker.entity.RoleDtl;
import com.lps.ldtracker.entity.SkillsDetail;
import com.lps.ldtracker.entity.UserDtl;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.model.UserDetail;
import com.lps.ldtracker.service.UserDtlService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
 
	private final UserDtlService userDtlService; 
	
	@GetMapping(value="/users/get-profile-information")
	public ResponseEntity<String> getProfileInformation() {
		return new ResponseEntity<>("Employee Test", HttpStatus.OK);
	}
	
	@GetMapping(value="/admin/users/get-users")
	public ResponseEntity<Result> getusers() {
		List<UserDtl> usr = this.userDtlService.getUserList();
		Result result = new Result();
		result.setData(usr);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(value="/users/get-profile-info/{id}")
	public ResponseEntity<Result> getProfileInformation(@PathVariable String id) {
		List<UserDetail> usr  = this.userDtlService.getUserById(id);
		Result result = new Result();
		result.setData(usr);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	
	}
	
	@GetMapping(value="/getUserRoles")
	public ResponseEntity<Result> getUserRoles() {
		Result result = new Result();
		try {
			List<RoleDtl> roleDtl =  this.userDtlService.getUserRoles();
			result.setData(roleDtl);
			result.setStatus("SUCCESS");
		} catch (Exception e) {
			result.setStatus("FAILED");
			e.printStackTrace();
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
