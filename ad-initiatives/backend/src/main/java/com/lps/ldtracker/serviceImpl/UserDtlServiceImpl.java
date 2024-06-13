package com.lps.ldtracker.serviceImpl;

import static com.lps.ldtracker.constants.LdTrackerConstants.ACCOUNT_VERIFIED;
import static com.lps.ldtracker.constants.LdTrackerConstants.BAD_REQUEST;
import static com.lps.ldtracker.constants.LdTrackerConstants.EMAIL;
import static com.lps.ldtracker.constants.LdTrackerConstants.EMAIL_SUFFIX;
import static com.lps.ldtracker.constants.LdTrackerConstants.ERROR;
import static com.lps.ldtracker.constants.LdTrackerConstants.ERROR_FETCH;
import static com.lps.ldtracker.constants.LdTrackerConstants.ERROR_OCCURED;
import static com.lps.ldtracker.constants.LdTrackerConstants.ERROR_REGISTER;
import static com.lps.ldtracker.constants.LdTrackerConstants.ERROR_RESET;
import static com.lps.ldtracker.constants.LdTrackerConstants.INVALID_EMAIL;
import static com.lps.ldtracker.constants.LdTrackerConstants.INVALID_PASSWORD;
import static com.lps.ldtracker.constants.LdTrackerConstants.INVALID_USERNAME;
import static com.lps.ldtracker.constants.LdTrackerConstants.MEMBERID;
import static com.lps.ldtracker.constants.LdTrackerConstants.PASSWORD;
import static com.lps.ldtracker.constants.LdTrackerConstants.SP_GETUSERINFO;
import static com.lps.ldtracker.constants.LdTrackerConstants.SUCCESS;
import static com.lps.ldtracker.constants.LdTrackerConstants.SUCCESS_PASSWORD_UPDATE;
import static com.lps.ldtracker.constants.LdTrackerConstants.USER;
import static com.lps.ldtracker.constants.LdTrackerConstants.USER_ALREADY_EXISTS;
import static com.lps.ldtracker.constants.LdTrackerConstants.USER_EXISTS;
import static com.lps.ldtracker.constants.LdTrackerConstants.USER_NAME;
import static com.lps.ldtracker.constants.LdTrackerConstants.USER_NOT_EXISTS;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.configuration.RealSessionAware;
import com.lps.ldtracker.entity.AccessLevel;
import com.lps.ldtracker.entity.ConfirmationDetail;
import com.lps.ldtracker.entity.RoleDtl;
import com.lps.ldtracker.entity.SkillsDetail;
import com.lps.ldtracker.entity.UserDtl;
import com.lps.ldtracker.exception.AuthenticationFailedException;
import com.lps.ldtracker.model.AuthenticationResponse;
import com.lps.ldtracker.model.LdTrackerError;
import com.lps.ldtracker.model.LoginRequest;
import com.lps.ldtracker.model.RegistrationRequest;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.model.UserDetail;
import com.lps.ldtracker.model.ValidationParamCollection;
import com.lps.ldtracker.repository.AccessLevelRepository;
import com.lps.ldtracker.repository.ConfirmationRepository;
import com.lps.ldtracker.repository.UserDtlRepository;
import com.lps.ldtracker.security.UserRegistrationDetails;
import com.lps.ldtracker.service.EmailService;
import com.lps.ldtracker.service.JwtService;
import com.lps.ldtracker.service.ResultService;
import com.lps.ldtracker.service.UserDtlService;

import jakarta.persistence.ParameterMode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDtlServiceImpl implements UserDtlService, UserDetailsService, RealSessionAware{

	private static final Logger logger =   LoggerFactory.getLogger(UserDtlServiceImpl.class);
	
	private final UserDtlRepository userDtlRepository;
	
	private final AccessLevelRepository accessLevelRepository;
	
	private final ConfirmationRepository confirmationRepository;
	
	private final EmailService emailService;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final ErrorHandlingService errorService;
	
	private final ResultService resultService;
	
	private static final String SP_GETUSERROLES = "sp_getUserRoles";

	@Autowired
	SessionFactory sessionFactory;
	
	@Value("${spring.mail.verify.login}")
	private String redirect;
	
	@Override
	public List<UserDtl> getUserList() { 
		return userDtlRepository.findAll();
	}

	@Override
	public Result registerUser(RegistrationRequest request) {
		Result result = new Result();
		Optional<UserDtl> user = this.findByUserName(request.username());
		List<LdTrackerError> errors = new ArrayList<>();
		try {
			this.validateRegisterInputs(request, errors);
			if (!errors.isEmpty()) {
				result = resultService.setResult(String.valueOf(BAD_REQUEST), ERROR_OCCURED, errors, null);
				return result;
			}
			if (user.isPresent()) {
				result.setMessage(USER_ALREADY_EXISTS);
				result.setStatus(ERROR);
				return result;
			} else {
				AccessLevel accLevel = accessLevelRepository.findByAlName(USER)
						.orElse(null);
				var userBuilder = UserDtl.builder()
						.userName(request.username())
						.userPass(passwordEncoder.encode(request.password()))
						.accessLevel(accLevel).isActive(false).isDeleted(false)
						.createdDate(Timestamp.valueOf(LocalDateTime.now())).build();
				UserDtl savedUserDtl = userDtlRepository.save(userBuilder);
				ConfirmationDetail confirmation = new ConfirmationDetail(savedUserDtl);
				confirmationRepository.save(confirmation);
				emailService.sendHtmlEmail(
						savedUserDtl.getUsername(), 
						savedUserDtl.getUsername().concat(EMAIL_SUFFIX),
						request.username(), request.password(),
						confirmation.getToken());
				var jwtToken = jwtService.generateToken(userBuilder);
				AuthenticationResponse
				.builder()
				.token(jwtToken)
				.build();
				result.setData(confirmation);
				result.setMessage(SUCCESS);
				result.setStatus(SUCCESS);
				return result;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ERROR_REGISTER + e.getMessage());

		}
		return result;
	}

	@Override
	public Result isExistUsername(LoginRequest request) {
		Result result = new Result();
		List<LdTrackerError> errors = new ArrayList<>();
		Boolean uDtl = userDtlRepository.existsByUserName(request.getUsername());
		this.validateUsernameInput(request, errors);
		if (!errors.isEmpty()) {
			result = resultService.setResult(String.valueOf(BAD_REQUEST), ERROR, errors, null);
			return result;
		}
		if(uDtl) {
			result.setData(uDtl);
			result.setMessage(USER_EXISTS);
			result.setStatus(SUCCESS);
		} else {
			result.setData(uDtl);
			result.setMessage(USER_NOT_EXISTS);
			result.setStatus(ERROR);						
		}		
		return result ;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { 
		return userDtlRepository.findByUserName(email)
			.map(UserRegistrationDetails::new)
			.orElseThrow(()-> new UsernameNotFoundException(USER_NOT_EXISTS));
	}

	@Override
	public Optional<UserDtl> findByUserName(String username) {
		return userDtlRepository.findByUserName(username);
	}

	private void validateRegisterInputs(RegistrationRequest param, List<LdTrackerError> errors) {
	    for (ValidationParamCollection<String, String, String> tuple : this.getValidationParams(param)) {
	        errorService.validateEmptyInputs(tuple.getFirst(), tuple.getSecond(), tuple.getThird(), errors);
	    }
	    // Additional specific validations
	    errorService.validateEmail(param.email(), EMAIL, INVALID_EMAIL, errors);
	    
	}
	
	private void validateUsernameInput(LoginRequest param, List<LdTrackerError> errors) {
		if(!param.getUsername().isEmpty()) {
			errorService.validateUserName(param.getUsername(), INVALID_USERNAME, errors);
		}
	}
	
	private List<ValidationParamCollection<String, String, String>> getValidationParams(RegistrationRequest param) {
		List<ValidationParamCollection<String, String, String>> validationTuples = new ArrayList<>();
	    validationTuples.add(new ValidationParamCollection<>(param.email(), EMAIL, INVALID_EMAIL));
	    validationTuples.add(new ValidationParamCollection<>(param.username(), USER_NAME, INVALID_USERNAME));
	    validationTuples.add(new ValidationParamCollection<>(param.password(), PASSWORD, INVALID_PASSWORD));
		return validationTuples;
	}
	
	@Override
	public Result resetPassword(RegistrationRequest request) {
		Result result = new Result();
		List<LdTrackerError> errors = new ArrayList<LdTrackerError>();
		Optional<UserDtl> user = this.findByUserName(request.username());
			if (!user.isPresent()) {
				errors.add(new LdTrackerError(INVALID_USERNAME, USER_NOT_EXISTS));
				result.setErrors(errors);
				result.setStatus(ERROR);
				return result;
			} else {
				try {
					UserDtl userDtl = user.get();
					userDtl.setUserPass(passwordEncoder.encode(request.password()));
					userDtl.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
					result.setData(userDtl);
					result.setMessage(SUCCESS_PASSWORD_UPDATE);
					result.setStatus(SUCCESS);
					userDtlRepository.save(userDtl);
					
					var jwtToken = jwtService.generateToken(userDtl);
					
					AuthenticationResponse
					.builder()
					.token(jwtToken)
					.build();
					
					return result;
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(ERROR_RESET + e.getMessage());
				}
			}

		return result;
	}

	@Override
	public String verifyToken(String token) {
		ConfirmationDetail confirmation = confirmationRepository.findByToken(token);
		UserDtl user = userDtlRepository.findByUserNameIgnoreCase(confirmation.getUser().getUsername());
		user.setIsActive(true);
		userDtlRepository.save(user);
		return String.format(ACCOUNT_VERIFIED, redirect);
	}
	
	@Override
	public String refreshToken(String username) {
		var userDtl = userDtlRepository.findByUserName(username)
				.orElseThrow(() -> {
			        throw new AuthenticationFailedException("User not found");
			    });
		return jwtService.generateRefreshToken(userDtl);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UserDetail> getUserById(String id) {

	    List<UserDetail> resList = new ArrayList<UserDetail>();
	    try  {
	    		Session session = getRealSession(sessionFactory);
	            ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETUSERINFO);
	            storedProcedureCall.registerStoredProcedureParameter(MEMBERID, String.class, ParameterMode.IN);
	            storedProcedureCall.setParameter(MEMBERID, id);
	            List<Object[]> recordList = storedProcedureCall.getResultList();
	                recordList.forEach(result -> {
	                    UserDetail res = new UserDetail();
	                    // Map the retrieved attributes to UserDetail object
	                    res.setLastName((String) result[0]);
	                    res.setFirstName((String) result[1]);
	                    res.setMiddleName((String) result[2]);
	                    res.setSuffix((String) result[3]);
	                    res.setGender((String) result[8]);
	                    res.setEmailAddress((String) result[9]);
	                    res.setCareerStep((String) result[10]);
	                    res.setEmployeeID((int) result[11]);
	                    res.setRegion((String) result[4]);
	                    // Assuming role and team information are available
	                    res.setRoles((String) result[5]);
	                    res.setTeams((String) result[6]);
	                    // Assuming employment status is available as a boolean
	                    res.setEmploymentStatus((String) result[7]);
	                    resList.add(res);
	                });
	        
	    } catch (Exception e) {
	        logger.error(ERROR_FETCH + e.getMessage(), e);
	    }
	    return resList;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	
	public List<RoleDtl> getUserRoles() { 
		Session session = getRealSession(sessionFactory);
		List<RoleDtl> skillsList = new ArrayList<RoleDtl>();
		try {
			ProcedureCall storedProcedureCall = session.createStoredProcedureCall(SP_GETUSERROLES);
			List<Object[]> recordList = storedProcedureCall.getResultList();
			recordList.forEach(result -> {
				RoleDtl res = new RoleDtl();
			    res.setRoleId((String) result[0]);
			    res.setRoleName((String) result[1]);    
			    res.setRoleDesc((String) result[2]);
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
