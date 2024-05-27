package emailOtpVerification.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import emailOtpVerification.requests.RegisterRequest;
import emailOtpVerification.responses.RegisterResponse;
import emailOtpVerification.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
		RegisterResponse registerResponse = userService.register(registerRequest);
		return new ResponseEntity<>(registerResponse, CREATED);
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> verifyUser(@RequestParam("email") String email, @RequestParam("otp") String otp) {
		try {
			userService.verify(email, otp);
			return new ResponseEntity<>("User verified successfully", OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
		}
	}
}
