package com.java.user_service.service;

import java.util.List;

import com.java.user_service.dto.request.UserRequest;
import com.java.user_service.dto.response.UserResponse;

public interface IUserService {
	
	UserResponse getUserById(String id);
    List<UserResponse> getUsers();
    String createUser(UserRequest request);
}
