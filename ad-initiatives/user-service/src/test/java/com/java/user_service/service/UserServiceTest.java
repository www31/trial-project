package com.java.user_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.java.user_service.dto.request.UserRequest;
import com.java.user_service.dto.response.UserResponse;
import com.java.user_service.entity.User;
import com.java.user_service.repository.UserRepository;
import com.java.user_service.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private IUserService userService = new UserServiceImpl();

	@Mock
	private UserRepository userRepository;

	@Test
	public void testGetUsers() throws IOException {
		List<User> users = getUsersDummyData();
		lenient().when(userRepository.findAll()).thenReturn(users);

		List<UserResponse> userList = userService.getUsers();
		assertTrue(null != userList);
		assertTrue(userList.size() == users.size());
	}

	@Test
	public void testCreateUser() {
		String randomUuid = UUID.randomUUID().toString();
		User user = new User();
		user.setId(randomUuid);
		
		UserRequest userRequest =  new UserRequest();
		userRequest.setEmail("test@abc.com");

		lenient().when(userRepository.existsByEmail(any(String.class))).thenReturn(false);
		lenient().when(userRepository.save(any(User.class))).thenReturn(user);

		assertEquals(userService.createUser(userRequest), user.getId());
	}
	
	@Test
	public void testInvalidCreateUser() {
		
		String result = userService.createUser(new UserRequest());
		assertEquals(result, "Invalid email");

		lenient().when(userRepository.existsByEmail(any(String.class))).thenReturn(true);
		
		UserRequest ur = new UserRequest();
		ur.setEmail("test");
		result = userService.createUser(ur);
		assertEquals(result, "Invalid request. Email already exists");
	}

	private List<User> getUsersDummyData() throws IOException {
		File file = new File("src/test/resources/users.csv");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		boolean isFirstRow = true;
		List<User> users = new ArrayList<User>();
		while ((line = br.readLine()) != null) {
			if (isFirstRow) {
				isFirstRow = false;
				continue;
			}
			String[] arr = line.split(",");
			User user = new User();
			user.setId(arr[0]);
			user.setFirstName(arr[1]);
			user.setLastName(arr[2]);
			user.setMiddleName(arr[3]);
			user.setSuffix(arr[4]);
			user.setEmail(arr[5]);
			user.setEmployeeId(arr[6]);
			user.setCareerStep(arr[7]);
			user.setLocation(arr[8]);
			user.setProjectId(arr[9]);
			user.setTeamId(arr[10]);

			user.setSupervisor(new User());
			user.getSupervisor().setId(arr[11]);

			user.setManager(new User());
			user.getManager().setId(arr[12]);

			user.setImage(arr[13]);
			user.setActive("1".equals(arr[14]));
			user.setCreatedDate(arr[15]);
			user.setLastUpdatedDate(arr[16]);
			users.add(user);
		}
		br.close();
		return users;
	}

}
