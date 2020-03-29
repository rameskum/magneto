package com.demo.magneto.security;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

import static com.demo.magneto.security.ApplicationUserPermissions.*;

public enum ApplicationUserRoles {

	USER(Sets.newHashSet()),
	SUPPORT(Sets.newHashSet(CONFIG_CREATE, CONFIG_READ, CONFIG_UPDATE, CONFIG_DELETE)),
	MANAGEMENT(Sets.newHashSet(CONFIG_READ, USER_READ)),
	ADMIN(Sets.newHashSet(CONFIG_CREATE, CONFIG_READ, CONFIG_UPDATE, CONFIG_DELETE, USER_CREATE, USER_READ, USER_UPDATE, USER_DELETE));

	@Getter
	private final Set<ApplicationUserPermissions> permissions;

	ApplicationUserRoles(Set<ApplicationUserPermissions> permissions) {
		this.permissions = permissions;
	}

	public Set<String> getGrantedAuthorities() {
		Set<String> simpleGrantedAuthorities = this.permissions.stream()
				.map(ApplicationUserPermissions::getPermission)
				.collect(Collectors.toSet());
		simpleGrantedAuthorities.add("ROLE_" + this.name());
		return simpleGrantedAuthorities;
	}
}
