package com.lps.ldtracker.service;

import java.util.List;

import com.lps.ldtracker.entity.StatusDetail;
import com.lps.ldtracker.model.CertTracking;
import com.lps.ldtracker.model.MemberCertDtl;
import com.lps.ldtracker.model.MemberInfo;
import com.lps.ldtracker.model.SkillName;

public interface ApproverService{
	
	List<MemberInfo> getTeamMemberList(String strUserID);
	List<MemberCertDtl> getMemberCertification(String strMemberID);
	List<MemberCertDtl> getTrainingsForApproval(String strMemberID);
	List<MemberCertDtl> getMemberTrainingList(String strMemberID);
	List<CertTracking> getCertTrackingDetails();
	List<SkillName> getSkillName();
	List<StatusDetail> getStatus();
}
