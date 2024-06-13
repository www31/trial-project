package com.lps.ldtracker.service;

import static com.lps.ldtracker.constants.LdTrackerConstants.ACCOUNT_INACTIVE;
import static com.lps.ldtracker.constants.LdTrackerConstants.MAX_PASSWORD_LENGTH;
import static com.lps.ldtracker.constants.LdTrackerConstants.MAX_USERNAME_LENGTH;
import static com.lps.ldtracker.constants.LdTrackerConstants.MIN_PASSWORD_LENGTH;
import static com.lps.ldtracker.constants.LdTrackerConstants.MIN_USERNAME_LENGTH;
import static com.lps.ldtracker.constants.LdTrackerConstants.PASSWORD_INCORRECT;
import static com.lps.ldtracker.constants.LdTrackerConstants.USER_NOT_EXISTS;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lps.ldtracker.exception.AuthenticationFailedException;
import com.lps.ldtracker.model.AuthenticationResponse;
import com.lps.ldtracker.model.LoginRequest;
import com.lps.ldtracker.repository.UserDtlRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserDtlRepository userDtlRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	
	public AuthenticationResponse login(LoginRequest loginRequest) throws AuthenticationFailedException, JsonProcessingException {
		String username = loginRequest.getUsername();
	    String password = loginRequest.getPassword();
	    
	    if (username.length() < MIN_USERNAME_LENGTH || username.length() > MAX_USERNAME_LENGTH) {
	        throw new AuthenticationFailedException("Username length must be between " + MIN_USERNAME_LENGTH + " and " + MAX_USERNAME_LENGTH + " characters");
	    }

	    if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
	        throw new AuthenticationFailedException("Password length must be between " + MIN_PASSWORD_LENGTH + " and " + MAX_PASSWORD_LENGTH + " characters");
	    }
		
		var userDtl = userDtlRepository.findByUserName(username)
			.orElseThrow(() -> {
		        throw new AuthenticationFailedException(USER_NOT_EXISTS);
		    });
		
		if (Boolean.FALSE.equals(userDtl.getIsActive())) {
            throw new AuthenticationFailedException(ACCOUNT_INACTIVE);
		}
		
		try {
			authenticationManager.authenticate(
		        new UsernamePasswordAuthenticationToken(
		        	username,
		        	password
		        )
		    );
		} catch (AuthenticationException authenticationException) {
			logger.error("username paswword auth exception",authenticationException);
			throw new AuthenticationFailedException(PASSWORD_INCORRECT);
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		String json = mapper.writeValueAsString(userDtl);
		Map<String, Object> map 
		  = mapper.readValue(json, new TypeReference<Map<String,Object>>(){});
		var jwtToken = jwtService.generateToken(map, userDtl);
		
		return AuthenticationResponse
			.builder()
			.token(jwtToken)
			.build();
	}
}