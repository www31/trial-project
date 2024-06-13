package com.lps.ldtracker.constants;

import org.springframework.http.HttpStatus;

public class LdTrackerConstants {
	
	//Ld Errors
	public static final String MISSING_PARAMETERS = "MISSING_PARAMETERS";
	public static final String INVALID_CHARACTERS = "INVALID_CHARACTERS";
	public static final String INVALID_PASSWORD = "INVALID_PASSWORD";
	public static final String INVALID_EMAIL = "INVALID_EMAIL";
	public static final String INVALID_USERNAME = "INVALID_USERNAME";
	public static final String INVALID_ADDRESS = "INVALID_ADDRESS";
	public static final String INVALID_STATUS = "INVALID_STATUS";
	public static final String INVALID_PHONE_NO = "INVALID_PHONE_NO";
	public static final String INVALID_POSITION = "INVALID_POSITION";
	public static final String INVALID_POSITION_CODE = "INVALID_POSITION_CODE";
	public static final String INVALID_FIRSTNAME= "INVALID_FIRSTNAME";
	public static final String INVALID_LASTNAME= "INVALID_LASTNAME";
	public static final String INVALID_ROLE= "INVALID_ROLE";
	public static final String ERROR_OCCURED = "ERROR_OCCURED";
	
	//validation params
	public static final String ADDRESS = "Address";
	public static final String EMAIL = "Email";
	public static final String USER_NAME = "User Name";
	public static final String PASSWORD = "Password";
	public static final String FIRST_NAME = "First Name";
	public static final String LAST_NAME = "Last Name";
	public static final String PHONE_NUMBER = "Phone number";
	public static final String POSITION = "Position";
	public static final String POSITION_CODE = "Position Code";
	public static final String ROLE = "Role";
	
	//response and catch errors
	public static final String ERROR_REGISTER = "ERROR registerUser:";
	public static final String ERROR_RESET = "ERROR resetPassword:";
	public static final String ERROR_FETCH = "Error occurred while fetching user details:";
	public static final Integer BAD_REQUEST = HttpStatus.BAD_REQUEST.value();

	public static final String USER_ALREADY_EXISTS = "User already exists.";
	public static final String ERROR = "error";
	public static final String SUCCESS = "success";
	
	//Email
	public static final String SUBJECT = "Email Verification";
	public static final String YOUR_OTP = "Your verification OTP is: ";
	public static final String ERROR_MAIL_MSG = "Error: sendEmail messaging ";
	public static final String ERROR_MAIL = "Error: sendEmail ";
	public static final String EMAIL_TEMPLATE = "emailTemplate";
	public static final String UTF_8_ENCODING = "UTF-8";
	public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
	public static final String EMAIL_SUFFIX = "@Lenovo.com";	
	public static final String ACCOUNT_VERIFIED = "Account verified click <a href=\"%1$s\">here</a> to login.";
	
	//Forgot PW
	public static final String USER_EXISTS = "Username found.";
	public static final String USER_NOT_EXISTS = "Username not found.";
	public static final String YOU_HAVE_NOT_SENT_OTP = "You have not sent otp";
	public static final String EXPIRED_OTP = "Expired otp";
	public static final String INVALID_OTP = "Invalid otp";
	public static final String EMPTY_FIELD= "Email/otp must not be empty";
	public static final String SUCCESS_PASSWORD_UPDATE= "Password successfully updated";
	public static final String AUTH_FAILED= "Authentication failed";
	public static final String AUTH_SUCCESS= "Authentication success";
	public static final String FIELD_MISSING= "Required field missing";
	public static final String PASSWORD_LENGTH= "Please enter a password between 10 and 20 characters in length";
	public static final String PASSWORD_INCORRECT = "Incorrect password";
	public static final String ACCOUNT_INACTIVE = "Your account is inactive";
	
	public static final Integer MIN_USERNAME_LENGTH = 5;
	public static final Integer MAX_USERNAME_LENGTH = 15;
	public static final Integer MIN_PASSWORD_LENGTH = 10;
	public static final Integer MAX_PASSWORD_LENGTH = 20;
	
	//Access level
	public static final String ADMIN = "Admin";
	public static final String USER = "User";
	public static final String APPROVER = "Approver";
	
	//Stored proc params
	public static final String SP_GETUSERINFO = "sp_getUserInfo";
	public static final String MEMBERID = "MemberID";
}
