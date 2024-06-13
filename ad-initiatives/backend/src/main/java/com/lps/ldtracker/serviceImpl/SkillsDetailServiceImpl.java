package com.lps.ldtracker.serviceImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.configuration.RealSessionAware;
import com.lps.ldtracker.entity.SkillsDetail;
import com.lps.ldtracker.repository.SkillsDetailRepository;
import com.lps.ldtracker.service.SkillsDetailService;

@Service
public class SkillsDetailServiceImpl implements SkillsDetailService, RealSessionAware{
	
	
	@Autowired
		SessionFactory sessionFactory;
	
	 @Autowired
	    private SkillsDetailRepository skillsDetailRepository;
	 
	 	private static final String SP_GETSKILLSLIST = "sp_getSkillsList";
	 

	    public List<SkillsDetail> getAllSkillDetails() {
	        return skillsDetailRepository.findAll();
	    }

		@Override
		@SuppressWarnings("unchecked")
		
		public List<SkillsDetail> getSkillDetails() { 
			Session session = getRealSession(sessionFactory);
			List<SkillsDetail> skillsList = new ArrayList<SkillsDetail>();
			try {
				ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETSKILLSLIST);
				List<Object[]> recordList = storedProcedureCall.getResultList();
				recordList.forEach(result -> {
					SkillsDetail res = new SkillsDetail();
				    res.setSkillId((String) result[0]);
				    res.setSkillName((String) result[1]);    
				    res.setSkillDesc((String) result[2]);
				    skillsList.add(res);		
				});
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	    		if(session != null) {
	    			session.close();
	    		}
	    	}
			return skillsList;
		}

}
