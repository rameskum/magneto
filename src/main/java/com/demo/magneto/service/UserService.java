package com.demo.magneto.service;

import com.demo.magneto.dto.UserDto;
import com.demo.magneto.entity.User;

import java.util.List;

public interface UserService {

	UserDto findUserByUsername(String username);

	UserDto findUserById(Long userId);

	User findUserEntityByUserName(String userName);

	void createUser(UserDto userDto);

	void updateUserDetail(UserDto userDto);

	void updateUserDetail(Long userId, UserDto userDto);

	void updateUserRoles(UserDto userDto);

	void changePassword(String oldPassword, String newPassword);

	void changeUserPassword(String userName, String newPassword);

	void deleteUser(String userName);

	void deleteUser(Long userId);

	boolean userExists(String userName);

	List<UserDto> getAllUsers();

	long totalUsersCount();

	long activeUsersCount();

	long inactiveUserCount();
}
