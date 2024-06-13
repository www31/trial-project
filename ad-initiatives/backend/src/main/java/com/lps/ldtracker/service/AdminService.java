package com.lps.ldtracker.service;

import java.util.List;

import com.lps.ldtracker.model.SkillDetail;

public interface AdminService{
	
	List<SkillDetail> getMemberSkillSet(String strMemberID);
	
}
