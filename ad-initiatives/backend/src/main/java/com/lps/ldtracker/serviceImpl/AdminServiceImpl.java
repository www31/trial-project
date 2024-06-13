package com.lps.ldtracker.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.configuration.RealSessionAware;
import com.lps.ldtracker.model.SkillDetail;
import com.lps.ldtracker.service.AdminService;

import jakarta.persistence.ParameterMode;

@Service
public class AdminServiceImpl implements AdminService, RealSessionAware {
	
	private static final String SP_GETMEMBERSKILLSET = "sp_getMemberSkillSet";
	
	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<SkillDetail> getMemberSkillSet(String strMemberID){
		List<SkillDetail> sdList = new ArrayList<SkillDetail>();
		Session session = getRealSession(sessionFactory);
		try {
			
			ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETMEMBERSKILLSET);
			storedProcedureCall.registerStoredProcedureParameter("P_MEMBERID", String.class, ParameterMode.IN);
			storedProcedureCall.setParameter("P_MEMBERID", strMemberID);
			List<Object[]> recordList = storedProcedureCall.getResultList();
			recordList.forEach(result -> {
				SkillDetail sd = new SkillDetail();
				sd.setType((String) result[0]);
				sd.setMemberId((String) result[1]);
				sd.setSkillName((String) result[2]);
				sdList.add(sd);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session!=null) {
				session.close();
			}
		}

		return sdList;
		
	}
}
