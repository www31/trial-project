package com.lps.ldtracker.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.constants.LdTrackerConstants;
import com.lps.ldtracker.model.LdTrackerError;
import com.lps.ldtracker.model.Result;
import com.lps.ldtracker.service.ResultService;

@Service
public class ErrorHandlingService {
	
	@Autowired
    private MessageSource messageSource;
	
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	private static final Pattern VALID_PHONE_NO_REGEX = Pattern.compile("\\d+");
	
	private static final Pattern VALID_USER_NAME_REGEX = Pattern.compile(".{6,}");

    public ResponseEntity<Result> createErrorResponse(String errorCode, String errorMessage, HttpStatus httpStatus) {
        List<LdTrackerError> errors = new ArrayList<>();
        LdTrackerError hrisError = new LdTrackerError(errorCode, errorMessage);
        errors.add(hrisError);

        ResultService resultService = new ResultServiceImpl();
        Result result = resultService.setResult(String.valueOf(httpStatus.value()), "Failure", errors, null);

        return new ResponseEntity<>(result, httpStatus);
    }
    
    public void validateInputParametersEmpId(String employeeId, List<LdTrackerError> errors) {
        if (employeeId == null || employeeId.isEmpty()) {
            errors.add(new LdTrackerError(LdTrackerConstants.INVALID_EMAIL, "employeeId is required"));
        }
    }
    
    public void validateCharactersInInput(String employeeId, List<LdTrackerError> errors) {
        // Validate employeeId
        if (!isValidEmployeeId(employeeId)) {
            errors.add(new LdTrackerError(LdTrackerConstants.INVALID_EMAIL, "employeeId contains special characters"));
        }
    }
    
    private boolean isValidEmployeeId(String employeeId) {
        // Validate if employeeId does not contain special characters
        String employeeIdRegex = "^[a-zA-Z0-9-]*$";
        return Pattern.matches(employeeIdRegex, employeeId);
    }
    
    public void validateEmail(String email, String label, String errorCode, List<LdTrackerError> errors) {  
    	Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email); 
    	if(!matcher.matches()) {
    		String[] msgParam = new String[1];
    		msgParam[0] = email;
    		errors.add(new LdTrackerError(errorCode, messageSource.getMessage("validation.format.message", msgParam, LocaleContextHolder.getLocale())));
    	}
    }
    
    public void validateEmptyInputs(String input, String label , String errorCode, List<LdTrackerError> errors) {
    	if(null == input || input.isEmpty()) {
    		String[] msgParam = new String[1];
    		msgParam[0] = label;
    		errors.add(new LdTrackerError(errorCode, messageSource.getMessage("validation.empty.message", msgParam, LocaleContextHolder.getLocale())));
    	}
    }
    
    public void validateCharLength(String str, String label, String errorCode, int maxLength, List<LdTrackerError> errors) {
    	if(str.length() > maxLength) {
    		String[] msgParam = new String[2];
    		msgParam[0] = label;
    		msgParam[1] = String.valueOf(maxLength);
    		errors.add(new LdTrackerError(errorCode, messageSource.getMessage("validation.charlength.message", msgParam, LocaleContextHolder.getLocale())));
    	}
    }
    
    public void validatePhoneNumber(String phoneNo, String label, String errorCode, List<LdTrackerError> errors) {  
    	Matcher matcher = VALID_PHONE_NO_REGEX.matcher(phoneNo); 
    	if(!matcher.matches()) {
    		String[] msgParam = new String[1];
    		msgParam[0] = phoneNo;
    		errors.add(new LdTrackerError(errorCode, messageSource.getMessage("validation.number.message", msgParam, LocaleContextHolder.getLocale())));
    	}
    }

	public void validateInputParametersId(String id, List<LdTrackerError> errors) {
		
		  if (id == null || id == "") {
	            errors.add(new LdTrackerError(LdTrackerConstants.MISSING_PARAMETERS, "Training ID is required"));
	        }
	}
    
    public void validateUserName(String userName, String errorCode, List<LdTrackerError> errors) {  
    	Matcher matcher = VALID_USER_NAME_REGEX.matcher(userName); 
    	if(!matcher.matches()) {
    		String[] msgParam = new String[1];
    		msgParam[0] = userName;
    		errors.add(new LdTrackerError(errorCode, messageSource.getMessage("validation.name.message", msgParam, LocaleContextHolder.getLocale())));
    	}
    }
    
}