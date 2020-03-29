package com.demo.magneto.security;

import lombok.Getter;

import java.util.Arrays;

public enum ApplicationUserPermissions {

	USER_CREATE("user:create"),
	USER_READ("user:read"),
	USER_UPDATE("user:update"),
	USER_DELETE("user:delete"),

	CONFIG_CREATE("config:create"),
	CONFIG_READ("config:read"),
	CONFIG_UPDATE("config:update"),
	CONFIG_DELETE("config:delete"),

	DEFAULT("none");

	@Getter
	private final String permission;

	ApplicationUserPermissions(String permission) {
		this.permission = permission;
	}

	public static ApplicationUserPermissions value(String value) {
		return Arrays.stream(ApplicationUserPermissions.values())
				.filter(per -> per.getPermission().equals(value))
				.findFirst().orElse(DEFAULT);
	}
}
