package com.demo.magneto.controller.v1.api;

import com.demo.magneto.dto.UserDto;
import com.demo.magneto.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
public class UserRestController {

	private UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("{userName}")
	public UserDto getUserDetails(@PathVariable final String userName) {
		return this.userService.findUserByUsername(userName);
	}
}
