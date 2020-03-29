package com.demo.magneto.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "security")
public class AdminProperties {

	private String adminUsername;

	private String adminPassword;

	@Value("${security.admin-first-name:FirstName}")
	private String adminFirstName;

	@Value("${security.admin-last-name:LastName}")
	private String adminLastName;
}
