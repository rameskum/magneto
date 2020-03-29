package com.demo.magneto.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface RoleManager {

	List<String> findAllRoles();

	List<String> findUsersInRole(String roleName);

	void createRole(String roleName, List<GrantedAuthority> authorities);

	void deleteRole(String roleName);

	void renameRole(String oldRole, String newRole);

	void addUserToRole(String roleName, String userName);

	void removeUserFromRole(String roleName, String userName);

	List<GrantedAuthority> findRoleAuthorities(String roleName);

	void addAuthority(String roleName, GrantedAuthority authority);

	void removeAuthority(String roleName, GrantedAuthority authority);
}
