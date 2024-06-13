/**
 * 
 */
package com.lps.ldtracker.model;

import java.sql.Timestamp;

import lombok.Builder;

/**
 * Login response record
 * @author 81258493
 * @version 1.0
 */
@Builder
public record LoginResponse(
		String userId, Boolean isActive, String userName, String userPass, Boolean isDeleted, String token,
		String createdBy, Timestamp createdDate, String updatedBy, Timestamp updatedDate, String accessName
		) {
	
}
