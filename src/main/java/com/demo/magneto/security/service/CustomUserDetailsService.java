package com.demo.magneto.security.service;

import com.demo.magneto.entity.User;
import com.demo.magneto.security.ApplicationUserPermissions;
import com.demo.magneto.security.ApplicationUserRoles;
import com.demo.magneto.service.UserService;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userName) {
		User user = userService.findUserEntityByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException(userName);
		}

		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getUserName())
				.password(user.getPassword())
				.disabled(!user.isActive())
				.authorities(resolvePermissions(user.getAuthorities()).split(","))
				.build();
	}

	private String resolvePermissions(String authorities) {
		if (authorities == null) {
			return String.join(",", ApplicationUserRoles.USER.getGrantedAuthorities());
		} else {
			return Arrays.stream(authorities.split(",")).map(permission -> {
				if (permission.startsWith("ROLE_")) {
					return ApplicationUserRoles.valueOf(permission.replace("ROLE_", "")).getGrantedAuthorities();
				} else {
					return Sets.newHashSet(ApplicationUserPermissions.value(permission).getPermission());
				}
			}).flatMap(Collection::stream)
					.collect(Collectors.joining(","));
		}
	}
}