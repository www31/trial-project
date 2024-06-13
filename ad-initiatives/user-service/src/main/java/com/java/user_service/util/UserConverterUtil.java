package com.java.user_service.util;

import org.springframework.beans.BeanUtils;

import com.java.user_service.constant.CareerStep;
import com.java.user_service.dto.response.UserResponse;
import com.java.user_service.entity.User;

public class UserConverterUtil {
	
	public static UserResponse toUserResponse(User user) {
		UserResponse ur = new UserResponse();
		BeanUtils.copyProperties(user, ur, "careerStep", "supervisor", "manager");
		ur.setCareerStep(CareerStep.getByCareerStep(user.getCareerStep()));
		if (null != user.getSupervisor()) {
			ur.setSupervisor(toUserResponse(user.getSupervisor()));
		}
		if (null != user.getManager()) {
			ur.setManager(toUserResponse(user.getManager()));
		}
		return ur;
	}
}
