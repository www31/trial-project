package com.java.user_service.dto.request;

import java.io.Serializable;

import com.java.user_service.constant.CareerStep;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest implements Serializable {

	private static final long serialVersionUID = 1945022962742980593L;

	private String firstName;
	private String lastName;
	private String middleName;
	private String suffix;
	private String email;
	private String employeeId;
	private CareerStep careerStep;
	private String location;
	private String projectId;
	private String teamId;
	private String supervisorId;
	private String managerId;
	private String image;
	private boolean active;
}
