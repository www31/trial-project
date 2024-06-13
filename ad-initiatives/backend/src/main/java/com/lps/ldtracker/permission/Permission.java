package com.lps.ldtracker.permission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
	
	USER_READ("user:read"),
	USER_CREATE("user:create"),
	USER_UPDATE("user:update"),
	USER_DELETE("user:delete"),
	ADMIN_READ("admin:read"),
	ADMIN_CREATE("admin:create"),
	ADMIN_UPDATE("admin:update"),
	ADMIN_DELETE("admin:delete"),
	APPROVER_READ("approver:read"),
	APPROVER_CREATE("approver:create"),
	APPROVER_UPDATE("approver:update"),
	APPROVER_DELETE("approver:delete");
	
	@Getter
	private final String permission;
}