package com.lps.ldtracker.security;

import static com.lps.ldtracker.permission.Permission.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.lps.ldtracker.permission.Permission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoleSecurity {
	
	GUEST(Collections.emptySet()),
	USER(
		Set.of(
			USER_READ,
			USER_CREATE,
			USER_UPDATE,
			USER_DELETE
		)
	),
	ADMIN(
		Set.of(
			ADMIN_READ,
			ADMIN_CREATE,
			ADMIN_UPDATE,
			ADMIN_DELETE
		)
	),
	APPROVER(
		Set.of(
			APPROVER_READ,
			APPROVER_CREATE,
			APPROVER_UPDATE,
			APPROVER_DELETE
		)
	);
	
	@Getter
	private final Set<Permission> permissions;
	
	public List<SimpleGrantedAuthority> getAuthorities() {
		var authorities = getPermissions()
			.stream()
			.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
			.collect(Collectors.toList());
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		
		return authorities;
	}
}