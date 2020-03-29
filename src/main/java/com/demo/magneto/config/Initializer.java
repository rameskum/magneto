package com.demo.magneto.config;

import com.demo.magneto.dto.UserDto;
import com.demo.magneto.security.AdminProperties;
import com.demo.magneto.security.ApplicationUserRoles;
import com.demo.magneto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {

	private AdminProperties adminProperties;

	@Autowired
	private UserService userService;

	public Initializer(AdminProperties adminProperties) {
		this.adminProperties = adminProperties;
	}

	@Override
	public void run(String... args) {
		UserDto adminUser = UserDto.builder()
				.userName(adminProperties.getAdminUsername())
				.password(adminProperties.getAdminPassword())
				.firstName(adminProperties.getAdminFirstName())
				.lastName(adminProperties.getAdminLastName())
				.active(true)
				.authorities(ApplicationUserRoles.ADMIN.getGrantedAuthorities())
				.build();

		if (this.userService.userExists(adminUser.getUserName())) {
			this.userService.updateUserDetail(adminUser);
			this.userService.changeUserPassword(adminUser.getUserName(), adminUser.getPassword());
			this.userService.updateUserRoles(adminUser);
		} else {
			this.userService.createUser(adminUser);
		}
	}
}
