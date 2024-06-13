package com.java.user_service.dto.response;

import com.java.user_service.constant.CareerStep;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

	private String id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String suffix;
    private String email;
    private String employeeId;
    private CareerStep careerStep;
    private String image;
    private String projectId;
    private String teamId;
    private UserResponse supervisor;
    private UserResponse manager;
    private boolean active;

}
