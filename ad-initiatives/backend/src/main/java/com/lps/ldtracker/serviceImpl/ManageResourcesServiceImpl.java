package com.lps.ldtracker.serviceImpl;
import java.nio.charset.StandardCharsets;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.configuration.RealSessionAware;
import com.lps.ldtracker.dto.ManageResourceDto;
import com.lps.ldtracker.model.MemberCertDtl;
import com.lps.ldtracker.model.ViewTrainingDetail;
import com.lps.ldtracker.service.ApproverService;
import com.lps.ldtracker.service.ManageResourcesService;
import com.lps.ldtracker.model.CertDetail;
import com.lps.ldtracker.model.UserDetail;

import jakarta.persistence.ParameterMode;

@Service
public class ManageResourcesServiceImpl implements ManageResourcesService, RealSessionAware{

	private static final String SP_GETRESOURCELIST = "sp_getResourceList";
	private static final String SP_GETVIEWTRAININGDETAILS = "sp_getTrainingDetails";
	private static final String SP_GETMEMBERCERTIFICATION = "sp_getMemberCertification";
	private static final String SP_GETUSERINFO = "sp_getUserInfo";
	private static final String SP_GETCERTDETAILLIST = "sp_getCertificationDetails";
	private static final String SP_GETCERTPERCERTNAME = "sp_getCertPerCertName"; 
	private static final String SP_GETCERTPERTEAM = "sp_getCertPerTeam";
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ManageResourceDto> getResourceList() {

		List<ManageResourceDto> resList = new ArrayList<ManageResourceDto>();
		Session session = getRealSession(sessionFactory);
		try {
			ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETRESOURCELIST);
			List<Object[]> recordList = storedProcedureCall.getResultList();
			recordList.forEach(result -> {
				ManageResourceDto res = new ManageResourceDto();
				res.setMemberId((String) result[0]);
				res.setMembername((String) result[1]);
				res.setEmployeeNum((Integer) result[2]);
				res.setRoleName((String) result[3]);
				res.setTeamName((String) result[4]);
				res.setMembertrainings((String) result[5]);
				//res.setMembercert((String) result[6]);
				List<String> cert = new ArrayList<String>();
				
				List<MemberCertDtl> lstCert = getMemberCertification((String) result[0]);
				for(MemberCertDtl i: lstCert) {
					cert.add(i.getCertName());
				}
				res.setCertifications(cert);
				resList.add(res);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		return resList;
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ViewTrainingDetail> getAllViewTrainingDetails() {
		
		List<ViewTrainingDetail> resList = new ArrayList<ViewTrainingDetail>();
		Session session = getRealSession(sessionFactory);
		try {
			ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETVIEWTRAININGDETAILS);
			List<Object[]> recordList = storedProcedureCall.getResultList();
			recordList.forEach(result -> {
				ViewTrainingDetail res = new ViewTrainingDetail();
				res.setId((String) result[0]);
				res.setName((String) result[1]);
				res.setStatus((String) result[2]);
				res.setDuration((String) result[3]);
				res.setProgressNumber((Integer) result[4]);
				res.setDate_started((String) result[5]);
				res.setDate_completed((String) result[6]);
				res.setEstimated_hours((Integer) result[7]);
				res.setDue_date((String) result[8]);
				res.setTarget_date((String) result[9]);
				res.setLink((String) result[10]);
				res.setRemarks((String) result[11]);
				res.setDescription((String) result[12]);

				resList.add(res);
			});
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		return resList;
	}
	
	@SuppressWarnings("unchecked")
	public List<MemberCertDtl> getMemberCertification(String strMemberID){
		List<MemberCertDtl> certDtl = new ArrayList<MemberCertDtl>();
		Session session = getRealSession(sessionFactory);
		try {
			ProcedureCall sp = session.createStoredProcedureCall(SP_GETMEMBERCERTIFICATION);
			sp.registerStoredProcedureParameter("P_MEMBERID", String.class, ParameterMode.IN);
			sp.setParameter("P_MEMBERID", strMemberID);
			List<Object[]> recordList = sp.getResultList();
			recordList.forEach(result -> {
				MemberCertDtl cd = new MemberCertDtl();
				cd.setMemberId((String) result[0]);
				cd.setCertID((String) result[1]);
				cd.setCertName((String) result[2]);
				cd.setDocID((String) result[3]);
				cd.setExpiryDate((String) result[4]);
				cd.setCompletionDate((String) result[5]);
				certDtl.add(cd);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		return certDtl;
	}
		
	@Override
	@SuppressWarnings("unchecked")
	public List<UserDetail> getUserById(String id) {
	    List<UserDetail> resList = new ArrayList<UserDetail>();
	    Session session = getRealSession(sessionFactory);
	    try  {
//	    		Session session = getRealSession(sessionFactory);
	            ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETUSERINFO);
	            storedProcedureCall.registerStoredProcedureParameter("MemberID", String.class, ParameterMode.IN);
	            storedProcedureCall.setParameter("MemberID", id);
	            List<Object[]> recordList = storedProcedureCall.getResultList();
	                recordList.forEach(result -> {
	                    UserDetail res = new UserDetail();
	                    res.setLastName((String) result[0]);
	                    res.setFirstName((String) result[1]);
	                    res.setMiddleName((String) result[2]);
	                    res.setSuffix((String) result[3]);
	                    res.setGender((String) result[8]);
	                    res.setEmailAddress((String) result[9]);
	                    res.setCareerStep((String) result[10]);
	                    res.setEmployeeID((int) result[11]);
	                    res.setRegion((String) result[4]);
	                    res.setRoles((String) result[5]);
	                    res.setTeams((String) result[6]);
	                    res.setEmploymentStatus((String) result[7]);
	                    resList.add(res);
	                });
	        
	    } catch (Exception e) {
	    	e.printStackTrace();
		}finally {
			if(session!=null) {
				session.close();
			}
		}
	    return resList;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<CertDetail> getCertDetailList() {
	    List<CertDetail> certDetailList = new ArrayList<>();
	    Session session = getRealSession(sessionFactory);
	    try {
//	        Session session = getRealSession(sessionFactory);
	        ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETCERTDETAILLIST);
	        List<Object[]> recordList = storedProcedureCall.getResultList();
	        recordList.forEach(result -> {
	            CertDetail certDetail = new CertDetail();
	            certDetail.setEmployeeNum((Integer) result[0]);
	            certDetail.setName((String) result[1]);
	            certDetail.setTeamName((String) result[2]);
	            certDetail.setCertName((String) result[3]);
	            certDetail.setCertLink((String) result[4]);
	            certDetail.setExpiryDate((Date) result[5]);
	            certDetail.setStatus((String) result[6]);
//	            byte[] fileContentBytes = (byte[]) result[7];
//	            String fileContentString = new String(fileContentBytes, StandardCharsets.UTF_8);
	            certDetail.setFileContent((String) result[7]);
	            certDetailList.add(certDetail);
	        });
	    } catch (Exception e) {
	        e.printStackTrace();
	    }finally {
			if(session!=null) {
				session.close();
			}
	    }
	    
	    return certDetailList;
	}	
	
	@Override
	@SuppressWarnings("unchecked")
    public List<CertDetail> getCertPerCertName() {
        List<CertDetail> certDetailList = new ArrayList<>();
        Session session = getRealSession(sessionFactory);
        try {
            ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETCERTPERCERTNAME);
            List<Object[]> recordList = storedProcedureCall.getResultList();
            recordList.forEach(result -> {
                CertDetail certDetail = new CertDetail();
                certDetail.setCertId((String) result[0]);
                certDetail.setCertName((String) result[1]);
                // Map other fields if needed
                certDetailList.add(certDetail);
            });
        } catch (Exception e) {
	        e.printStackTrace();
	    }finally {
			if(session!=null) {
				session.close();
			}
	    }
        return certDetailList;
    }
	
	@Override
	@SuppressWarnings("unchecked")
    public List<CertDetail> getCertPerTeam() {
        List<CertDetail> certDetailList = new ArrayList<>();
        Session session = getRealSession(sessionFactory);
        try {
            ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETCERTPERTEAM);
            List<Object[]> recordList = storedProcedureCall.getResultList();
            recordList.forEach(result -> {
                CertDetail certDetail = new CertDetail();
                certDetail.setTeamId((String) result[0]);
                certDetail.setTeamName((String) result[1]);
                certDetail.setTeamCode((String) result[2]);
                // Map other fields if needed
                certDetailList.add(certDetail);
            });
        } catch (Exception e) {
	        e.printStackTrace();
	    }finally {
			if(session!=null) {
				session.close();
			}
	    }
        return certDetailList;
    }

}