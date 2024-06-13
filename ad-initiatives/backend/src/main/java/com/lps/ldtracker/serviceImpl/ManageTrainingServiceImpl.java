package com.lps.ldtracker.serviceImpl;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.configuration.RealSessionAware;
import com.lps.ldtracker.dto.ManageTrainingDto;
import com.lps.ldtracker.dto.TrainingLinksDto;
import com.lps.ldtracker.entity.Training_Dtl;
import com.lps.ldtracker.model.MemberInfo;
import com.lps.ldtracker.model.SkillSet;
import com.lps.ldtracker.repository.TrainingRepository;
import com.lps.ldtracker.service.ManageTrainingService;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.ParameterMode;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ManageTrainingServiceImpl  implements ManageTrainingService, RealSessionAware{
	
	private final TrainingRepository trainingRepository;
	
	private static final String SP_GETTRAININGLIST = "sp_getTrainingList";
	private static final String SP_SETTRAININGDETAILS = "sp_setTrainingDetails";
	private static final String SP_SETTRAININGLINKS = "sp_setTrainingLinks";
	private static final String SP_DELETETRAINING = "sp_deleteTraining";
	private static final String SP_GETTRAININGLINKS = "sp_getTrainingLinks";
	private static final String SP_GETTRAININGSBYUSER = "sp_getTrainingsbyUser";
	private static final String SP_GETTRAININGSBYCRITERIA = "sp_getTrainingsbyCriteria";
	private static final String SP_SETUSERTRAININGS = "sp_setUserTrainings";
	
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ManageTrainingDto> getTrainingList() { 
		List<ManageTrainingDto> resList = new ArrayList<ManageTrainingDto>();
		Session session = getRealSession(sessionFactory);
		try {
			ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETTRAININGLIST);
			List<Object[]> recordList = storedProcedureCall.getResultList();
			recordList.forEach(result -> {
			    ManageTrainingDto res = new ManageTrainingDto();
			    res.setId((String) result[0]);
			    res.setTrainingName((String) result[1]);    
			    res.setTrainingType((String) result[2]);    
			    res.setProductName((String) result[3]);
			    res.setStartDate((Date) result[4]);
			    res.setDueDate((Date) result[5]);
			    res.setPreReq((String) result[6]);
			    res.setDescription((String) result[7]);
			    res.setTrainingTags((String) result[8]);
			    res.setIsRequired((Boolean) result[9]);
			    res.setCertification((Boolean) result [10]);
			    res.setCertID((String) result [11]);
			    res.setCertName((String) result[12]);
			    res.setDuration((String) result[13]);
			    res.setFee((Integer) result[14]);
			    res.setCurrency((String) result [15]);
			    res.setCertLink((String) result[16]);
			    res.setTrCondition((Integer) result[17]);
			    res.setTrConditionValue((String) result[18]);
			    res.setActive((Boolean) result[19]); 
			    res.setExpiryDate((Date) result[20]);
			    resList.add(res);		
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
    		if(session != null) {
    			session.close();
    		}
    	}
		return resList;
		
		
	}	
	
	@Override
	public void deleteTrainingById(Integer Id) {
		Optional<Training_Dtl> existingTraining = trainingRepository.findById(Id);
		existingTraining.ifPresent(trainingRepository::delete);
	}
	
	@Override
	public String deleteTraining(String trainingId) {
	    try {
	        Session session = getRealSession(sessionFactory);
	        ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_DELETETRAINING);
	        
	        // Register input parameter
	        storedProcedureCall.registerStoredProcedureParameter("TrainingID", String.class, ParameterMode.IN);
	        storedProcedureCall.registerStoredProcedureParameter("Message", String.class, ParameterMode.OUT);
	        
	        // Set input parameter
	        storedProcedureCall.setParameter("TrainingID", trainingId);
	        
	        // Execute the stored procedure
	        ProcedureOutputs procedureOutputs = storedProcedureCall.getOutputs();
	        
	        // Get output parameter
	        Object output = procedureOutputs.getOutputParameterValue("Message");
	        
	        // Check if output is null
	        if (output != null) {
	            return output.toString();
	        } else {
	            return "No message returned from the stored procedure.";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Error occurred while deleting training record.";
	    }
	}


    @Override
    public ManageTrainingDto saveTraining(ManageTrainingDto trainingDetails) {
    	Session session = getRealSession(sessionFactory);
        try {
            ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_SETTRAININGDETAILS);
            storedProcedureCall.registerStoredProcedureParameter("P_TRAININGID", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_TRAININGNAME", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_TRAININGTYPE", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_PRODUCTNAME", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_STARTDATE", Date.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_DUEDATE", Date.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_PREREQ", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_TRAININGDESC", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_TRAININGTAGS", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_ISREQIURED", Boolean.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_CERTFLG", Boolean.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_TRCONDITION", Integer.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_TRCONDITIONVALUE", String.class, ParameterMode.IN);
            
            storedProcedureCall.registerStoredProcedureParameter("P_CERTID", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_CERTNAME", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_DURATION", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_FEE", Integer.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_CURRENCY", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_CERTLINK", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_EXPIRYDATE", Date.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_RENEWABLE", Boolean.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_SKILLID", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_CREATEDBY", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("OUT_TID", String.class, ParameterMode.OUT);
            storedProcedureCall.registerStoredProcedureParameter("OUT_CID", String.class, ParameterMode.OUT);

            storedProcedureCall.setParameter("P_TRAININGID", trainingDetails.getId());
            storedProcedureCall.setParameter("P_TRAININGNAME", trainingDetails.getTrainingName());
            storedProcedureCall.setParameter("P_TRAININGTYPE", trainingDetails.getTrainingType());
            storedProcedureCall.setParameter("P_PRODUCTNAME", trainingDetails.getProductName());
            storedProcedureCall.setParameter("P_STARTDATE", trainingDetails.getStartDate());
            storedProcedureCall.setParameter("P_DUEDATE", trainingDetails.getDueDate());
            storedProcedureCall.setParameter("P_PREREQ", trainingDetails.getPreReq());
            storedProcedureCall.setParameter("P_TRAININGDESC", trainingDetails.getDescription());
            storedProcedureCall.setParameter("P_TRAININGTAGS", trainingDetails.getTrainingTags());
            storedProcedureCall.setParameter("P_ISREQIURED", trainingDetails.getIsRequired());
            storedProcedureCall.setParameter("P_CERTFLG", trainingDetails.getCertification());
            storedProcedureCall.setParameter("P_TRCONDITION", trainingDetails.getTrCondition());
            storedProcedureCall.setParameter("P_TRCONDITIONVALUE", trainingDetails.getTrConditionValue());
            
            storedProcedureCall.setParameter("P_CERTID", trainingDetails.getCertID());
            storedProcedureCall.setParameter("P_CERTNAME", trainingDetails.getCertName());
            storedProcedureCall.setParameter("P_DURATION", trainingDetails.getDuration());
            storedProcedureCall.setParameter("P_FEE", trainingDetails.getFee());
            storedProcedureCall.setParameter("P_CURRENCY", trainingDetails.getCurrency());
            storedProcedureCall.setParameter("P_CERTLINK", trainingDetails.getCertLink());
            storedProcedureCall.setParameter("P_EXPIRYDATE", trainingDetails.getExpiryDate());
            storedProcedureCall.setParameter("P_RENEWABLE", trainingDetails.getRenewable());
            storedProcedureCall.setParameter("P_SKILLID", trainingDetails.getSkillID());
            storedProcedureCall.setParameter("P_CREATEDBY", trainingDetails.getCreatedBy());
            storedProcedureCall.execute();
            
            //saveTrainingLinks(trainingDetails.getTrainingLinks());
            String strTrainingID = (String) storedProcedureCall.getOutputs().getOutputParameterValue("OUT_TID");
            if(strTrainingID != null) {
            	trainingDetails.setId(strTrainingID);
            }
            String strCertID = (String) storedProcedureCall.getOutputs().getOutputParameterValue("OUT_CID");
            if(strCertID != null) {
            	trainingDetails.setCertID(strCertID);
            }
            
            System.out.println("strTrainingID " + strTrainingID);
            System.out.println("strCertID " + strCertID);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
    		if(session != null) {
    			session.close();
    		}
    	}
        return trainingDetails;
    }
    
    public List<TrainingLinksDto> saveTrainingLinks(List<TrainingLinksDto> trainingLinks) {
    	Session session = getRealSession(sessionFactory);
    	try {
    		if (trainingLinks != null) {
            for (TrainingLinksDto links : trainingLinks) {
                ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_SETTRAININGLINKS);
                storedProcedureCall.registerStoredProcedureParameter("P_SUBTRLINK_ID", Integer.class, ParameterMode.IN);
                storedProcedureCall.registerStoredProcedureParameter("P_SUBTR_NAME", String.class, ParameterMode.IN);
                storedProcedureCall.registerStoredProcedureParameter("P_SUBTR_LINK", String.class, ParameterMode.IN);
                storedProcedureCall.registerStoredProcedureParameter("P_TRAININGID", String.class, ParameterMode.IN);
                storedProcedureCall.registerStoredProcedureParameter("P_CREATEDBY", String.class, ParameterMode.IN);
                storedProcedureCall.registerStoredProcedureParameter("OUT_TID", String.class, ParameterMode.OUT);
     
                storedProcedureCall.setParameter("P_SUBTRLINK_ID", links.getSubTrId());
                storedProcedureCall.setParameter("P_SUBTR_NAME", links.getSubTrName());
                storedProcedureCall.setParameter("P_SUBTR_LINK", links.getSubTrLink());
                storedProcedureCall.setParameter("P_TRAININGID", links.getTrainingId());
                storedProcedureCall.setParameter("P_CREATEDBY", links.getCreatedBy());
     
                storedProcedureCall.execute();
                String strLinkID = (String) storedProcedureCall.getOutputs().getOutputParameterValue("OUT_TID");
                System.out.println("trainingID: " + strLinkID + " subTrId: " + links.getSubTrId());
            	}
     
    		}
        } catch (Exception e) {
        	e.printStackTrace();
    	} finally {
    		if(session != null) {
    			session.close();
    		}
    	}
    	return trainingLinks;
    }

	@SuppressWarnings("unchecked")
	public List<TrainingLinksDto> getTrainingLinks(String id) {
	    List<TrainingLinksDto> trLinksList = new ArrayList<TrainingLinksDto>();
	    Session session = getRealSession(sessionFactory);
	    try {
	        ProcedureCall sp = session.createStoredProcedureCall(SP_GETTRAININGLINKS);
	        sp.registerStoredProcedureParameter("P_TRAININGID", String.class, ParameterMode.IN);
	        sp.setParameter("P_TRAININGID", id);
	        List<Object[]> recordList = sp.getResultList();
	        recordList.forEach(result -> {
	            TrainingLinksDto tr = new TrainingLinksDto();
	            tr.setSubTrId((Integer) result[0]);
	            tr.setTrainingId((String) result[1]);
	            tr.setSubTrName((String) result[2]);
	            tr.setSubTrLink((String) result[3]);

	            trLinksList.add(tr);
	        });
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }
	    System.out.println(trLinksList);
	    return trLinksList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ManageTrainingDto> getTrainingsbyUser() { 
		List<ManageTrainingDto> resList = new ArrayList<ManageTrainingDto>();
		Session session = getRealSession(sessionFactory);
		try {
			ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETTRAININGSBYUSER);
			List<Object[]> recordList = storedProcedureCall.getResultList();
			recordList.forEach(result -> {
			    ManageTrainingDto res = new ManageTrainingDto();
			    res.setId((String) result[0]);
			    res.setTrainingName((String) result[1]); 
			    res.setDescription((String) result [2]);
			    res.setCertification((Boolean) result [3]);
			    res.setIsRequired((Boolean) result[4]);
			    res.setDueDate((Date) result[5]);
			    resList.add(res);		
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
    		if(session != null) {
    			session.close();
    		}
    	}
		return resList;
	}	
	
	
	@SuppressWarnings("unchecked")
	public List<ManageTrainingDto> searchTrainingByCriteria(String trainingName, String skillName, String startDate, String endDate) {
	    List<ManageTrainingDto> trainingList = new ArrayList<ManageTrainingDto>();
	    Session session = getRealSession(sessionFactory);
	    try {
	        ProcedureCall sp = session.createStoredProcedureCall(SP_GETTRAININGSBYCRITERIA);
	        sp.registerStoredProcedureParameter("P_TRAININGNAME", String.class, ParameterMode.IN);
	        sp.registerStoredProcedureParameter("P_SKILLNAME", String.class, ParameterMode.IN);
	        sp.registerStoredProcedureParameter("P_STARTDT", String.class, ParameterMode.IN);
	        sp.registerStoredProcedureParameter("P_ENDDT", String.class, ParameterMode.IN);
	        
	        sp.setParameter("P_TRAININGNAME", trainingName);
	        sp.setParameter("P_SKILLNAME", skillName);
	        sp.setParameter("P_STARTDT", startDate);
	        sp.setParameter("P_ENDDT", endDate);
	        
	        List<Object[]> recordList = sp.getResultList();
	        recordList.forEach(result -> {
	            ManageTrainingDto tr = new ManageTrainingDto();
	            tr.setTrainingId((String) result[0]);
	            tr.setTrainingName((String) result[1]);
	            tr.setDescription((String) result[2]);
	            tr.setCertification((Boolean) result[3]);
	            tr.setTrainingType((String) result[4]);
	            tr.setStartDate((Date) result [5]);
	            tr.setDueDate((Date) result [6]);

	            trainingList.add(tr);
	        });
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }
	    System.out.println(trainingList);
	    return trainingList;
	}
	
    @Override
    public SkillSet registerUserTraining(SkillSet userTraining) {
    	Session session = getRealSession(sessionFactory);
        try {
            ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_SETUSERTRAININGS);
            storedProcedureCall.registerStoredProcedureParameter("P_SSID", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_MEMBERID", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_TRAININGID", String.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("P_SKILLID", Date.class, ParameterMode.IN);
            storedProcedureCall.registerStoredProcedureParameter("OUT_SSID", Date.class, ParameterMode.OUT);
            

            storedProcedureCall.setParameter("P_SSID", userTraining.getSsId());
            storedProcedureCall.setParameter("P_MEMBERID", userTraining.getMemberId());
            storedProcedureCall.setParameter("P_TRAININGID", userTraining.getTrainingId());
            storedProcedureCall.setParameter("P_SKILLID", userTraining.getSkillId());

            storedProcedureCall.execute();
            
            //saveTrainingLinks(trainingDetails.getTrainingLinks());
            String strSsId = (String) storedProcedureCall.getOutputs().getOutputParameterValue("OUT_SSID");
            if(strSsId != null) {
            	userTraining.setSsId(strSsId);
            }
            
            System.out.println("strTrainingID " + strSsId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
    		if(session != null) {
    			session.close();
    		}
    	}
        return userTraining;
    }
	
}


