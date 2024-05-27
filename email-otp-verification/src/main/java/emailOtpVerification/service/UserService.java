package emailOtpVerification.service;

import emailOtpVerification.requests.RegisterRequest;
import emailOtpVerification.responses.RegisterResponse;

public interface UserService {
	RegisterResponse register(RegisterRequest registerRequest);
	void verify(String email, String otp);
}
