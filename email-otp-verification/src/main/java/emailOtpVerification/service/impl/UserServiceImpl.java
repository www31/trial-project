package emailOtpVerification.service.impl;

import java.util.Random;

import org.springframework.stereotype.Service;

import emailOtpVerification.model.Users;
import emailOtpVerification.repository.UsersRepository;
import emailOtpVerification.requests.RegisterRequest;
import emailOtpVerification.responses.RegisterResponse;
import emailOtpVerification.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UsersRepository usersRepository;
	
	private final EmailService emailService;

	@Override
	public RegisterResponse register(RegisterRequest registerRequest) {
		Users existingUser = usersRepository.findByEmail(registerRequest.getEmail());
		if (existingUser != null && existingUser.isVerified()) {
			throw new RuntimeException("User already registered");
		}
		Users users = Users.builder()
				.userName(registerRequest.getUserName())
				.email(registerRequest.getEmail())
				.password(registerRequest.getPassword())
				.build();
		String otp = generateOTP();
		users.setOtp(otp);
		Users savedUser = usersRepository.save(users);
		sendVerificationEmail(savedUser.getEmail(), otp);
		RegisterResponse response = RegisterResponse.builder()
				.userName(users.getUserName())
				.email(users.getEmail())
				.build();
		return response;
	}
	
	private String generateOTP() {
		Random random = new Random();
		int otpValue = 10000 + random.nextInt(900000);
		return String.valueOf(otpValue);
	}
	
	private void sendVerificationEmail(String email, String otp) {
		String subject = "Email verification";
		String body = "your verification otp is: " + otp;
		emailService.sendEmail(email, subject, body);
	}

	@Override
	public void verify(String email, String otp) {
		Users users = usersRepository.findByEmail(email);
		if (users == null) {
			throw new RuntimeException("User not found");
		} else if (users.isVerified()) {
			throw new RuntimeException("User is already verified");
		} else if (otp.equals(users.getOtp())) {
			users.setVerified(true);
			usersRepository.save(users);
		} else {
			throw new RuntimeException("Internal server error");
		}
		
	}
}
