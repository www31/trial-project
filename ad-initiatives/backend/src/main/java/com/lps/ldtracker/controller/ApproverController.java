package com.lps.ldtracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lps.ldtracker.entity.StatusDetail;
import com.lps.ldtracker.model.CertTracking;
import com.lps.ldtracker.model.MemberCertDtl;
import com.lps.ldtracker.model.MemberInfo;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.model.SkillName;
import com.lps.ldtracker.service.ApproverService;

@RestController
@RequestMapping("/api/v1/approver")
public class ApproverController {
	
	@Autowired
	private ApproverService approverSrvc;
	
	@PostMapping(value="/getTeamMemberList")
	public ResponseEntity<Result> getTeamMemberList(@RequestBody String strUserID) {
		Result result = new Result();
		try {
			System.out.println("strUserID: " + strUserID);
			List<MemberInfo> userList = approverSrvc.getTeamMemberList(strUserID);
			result.setData(userList);
			result.setStatus("SUCCESS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setStatus("FAILED");
			e.printStackTrace();
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping(value="/getMemberCertification")
	public ResponseEntity<Result> getMemberCertification(@RequestBody String strMemberID) {
		Result result = new Result();
		try {
			System.out.println("strMemberID: " + strMemberID);
			List<MemberCertDtl> certLst = approverSrvc.getMemberCertification(strMemberID);
			result.setData(certLst);
			result.setStatus("SUCCESS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setStatus("FAILED");
			e.printStackTrace();
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping(value="/getTrainingsForApproval")
	public ResponseEntity<Result> getTrainingsForApproval(@RequestBody String strMemberID) {
		Result result = new Result();
		try {
			System.out.println("strMemberID: " + strMemberID);
			List<MemberCertDtl> approvalLst = approverSrvc.getTrainingsForApproval(strMemberID);
			result.setData(approvalLst);
			result.setStatus("SUCCESS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setStatus("FAILED");
			e.printStackTrace();
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping(value="/getMemberTrainingList")
	public ResponseEntity<Result> getMemberTrainingList(@RequestBody String strMemberID) {
		Result result = new Result();
		try {
			System.out.println("strMemberID: " + strMemberID);
			List<MemberCertDtl> approvalLst = approverSrvc.getMemberTrainingList(strMemberID);
			result.setData(approvalLst);
			result.setStatus("SUCCESS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setStatus("FAILED");
			e.printStackTrace();
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(value="/getCertTrackingDtl")
	public ResponseEntity<Result> getCertTrackingDtl() {
		Result result = new Result();
		try {
			List<CertTracking> certTrackingDtl = this.approverSrvc.getCertTrackingDetails();
			result.setData(certTrackingDtl);
			result.setStatus("SUCCESS");
		} catch (Exception e) {
			result.setStatus("FAILED");
			e.printStackTrace();
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(value="/getSkillName")
	public ResponseEntity<Result> getSkillName() {
		Result result = new Result();
		try {
			List<SkillName> skillNameDtl = this.approverSrvc.getSkillName();
			result.setData(skillNameDtl);
			result.setStatus("SUCCESS");
		} catch (Exception e) {
			result.setStatus("FAILED");
			e.printStackTrace();
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(value="/getStatus")
	public ResponseEntity<Result> getStatus() {
		Result result = new Result();
		try {
			List<StatusDetail> statusDtl = this.approverSrvc.getStatus();
			result.setData(statusDtl);
			result.setStatus("SUCCESS");
		} catch (Exception e) {
			result.setStatus("FAILED");
			e.printStackTrace();
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
