package com.demo.magneto.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class UserDto {

	private Long id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private boolean active;
	private Set<String> authorities;

	public String authoritiesStr() {
		if (authorities == null || authorities.isEmpty())
			return "";
		else
			return String.join(",", authorities);
	}
}
