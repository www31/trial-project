package com.java.user_service.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.user_service.dto.request.UserRequest;
import com.java.user_service.dto.response.UserResponse;
import com.java.user_service.entity.User;
import com.java.user_service.repository.UserRepository;
import com.java.user_service.service.IUserService;
import com.java.user_service.util.UserConverterUtil;
import com.java.user_service.util.Validations;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserResponse getUserById(String userId) {
		return getUserById(userId, true, true);
	}

	@Override
	public List<UserResponse> getUsers() {
		final List<User> usersFromDb = userRepository.findAll();
		final List<UserResponse> users = new ArrayList<UserResponse>();

		usersFromDb.forEach(u -> {
			UserResponse user = UserConverterUtil.toUserResponse(u);
			users.add(user);
		});
		return users;
	}

	@Override
	public String createUser(UserRequest request) {
		if (Validations.isTextEmpty(request.getEmail())) {
			return "Invalid email";
		}
		
		boolean exists = userRepository.existsByEmail(request.getEmail());
		if (exists) {
			return "Invalid request. Email already exists";
		}
		
		User user = new User();
		BeanUtils.copyProperties(request, user, "careerStep");
		user.setCareerStep(null != request.getCareerStep() ? request.getCareerStep().getCareerStep() : null);
		user.setActive(true);
		user = userRepository.save(user);

		return user.getId();
	}

	private UserResponse getUserById(String userId, boolean includeSupervisorDetails, boolean includeManagerDetails) {
		if (Validations.isTextEmpty(userId)) {
			return null;
		}

		User user = userRepository.findById(userId).get();
		if (null == user) {
			return null;
		}

		return UserConverterUtil.toUserResponse(user);
	}
}
