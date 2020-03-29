package com.demo.magneto.controller.v1.ui;

import com.demo.magneto.dto.UserDto;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CommonControllerAdvise {

	@ModelAttribute("principal")
	public UserDto principalAttribute() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			return UserDto.builder().build();
		} else {
			String authenticationName = authentication.getName();
			return UserDto.builder().build();
		}
	}
}
