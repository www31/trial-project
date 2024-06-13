package com.java.user_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.user_service.dto.request.UserRequest;
import com.java.user_service.dto.response.UserResponse;
import com.java.user_service.service.IUserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private IUserService userService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<UserResponse> getUserList() {
		return userService.getUsers();
	}

	@PostMapping
	public @ResponseBody String createUser(@RequestBody UserRequest userRequest) {
		return userService.createUser(userRequest);
	}
}
