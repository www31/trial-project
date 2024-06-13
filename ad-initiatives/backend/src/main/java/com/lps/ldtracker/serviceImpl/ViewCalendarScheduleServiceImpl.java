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
import com.lps.ldtracker.dto.ViewCalenderScheduleDto;
import com.lps.ldtracker.service.ViewCalendarScheduleService;

@Service
public class ViewCalendarScheduleServiceImpl implements ViewCalendarScheduleService, RealSessionAware{
	
	private static final String SP_GETCALENDARSCHEDULE = "sp_getTrainingSchedule";
	
	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<ViewCalenderScheduleDto> getViewCalendarSchedule(){
		List<ViewCalenderScheduleDto> csList = new ArrayList<ViewCalenderScheduleDto>();
		Session session = getRealSession(sessionFactory);
		try {
			
			ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETCALENDARSCHEDULE);
			List<Object[]> recordList = storedProcedureCall.getResultList();
			recordList.forEach(result -> {
				ViewCalenderScheduleDto res = new ViewCalenderScheduleDto();
			    res.setTrainingName((String) result[0]);
			    res.setStartDate((Date) result[1]);
			    res.setDueDate((Date) result[2]);
			    res.setTrainingDesc((String) result[3]);
			    res.setTrainingId((String) result[4]);
			    csList.add(res);		
			});
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		return csList;
		
	}

}
