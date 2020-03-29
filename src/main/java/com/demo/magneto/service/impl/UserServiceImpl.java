package com.demo.magneto.service.impl;

import com.demo.magneto.dto.UserDto;
import com.demo.magneto.entity.User;
import com.demo.magneto.exception.EntityType;
import com.demo.magneto.exception.ExceptionHandler;
import com.demo.magneto.exception.ExceptionType;
import com.demo.magneto.repositories.UserRepository;
import com.demo.magneto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private ExceptionHandler exceptionHandler;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, ExceptionHandler exceptionHandler) {
		this.userRepository = userRepository;
		this.exceptionHandler = exceptionHandler;
	}

	@Nullable
	@Override
	public UserDto findUserByUsername(String userName) {
		User userEntity = this.userRepository.findByUserName(userName);
		if (userEntity == null)
			return null;
		return this.toUserDao(userEntity);
	}

	@Override
	public UserDto findUserById(Long userId) {
		Optional<User> byId = this.userRepository.findById(userId);
		return byId.map(this::toUserDao).orElse(null);
	}

	@Override
	public User findUserEntityByUserName(String userName) {
		return this.userRepository.findByUserName(userName);
	}

	@Override
	public void createUser(UserDto userDto) {
		Assert.notNull(userDto, "userDao for createUser cannot be null");
		if (this.userExists(userDto.getUserName())) {
			throw this.exceptionHandler.throwException(EntityType.USER, ExceptionType.DUPLICATE_ENTITY, userDto.getUserName());
		}
		User newUser = this.toUser(userDto);
		this.userRepository.save(newUser);
	}

	@Override
	public void updateUserDetail(UserDto userDto) {
		Assert.notNull(userDto, "userDao for updateUserDetail cannot be null");
		if (!this.userExists(userDto.getUserName())) {
			throw this.exceptionHandler.throwException(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getUserName());
		}
		User userEntity = this.userRepository.findByUserName(userDto.getUserName());
		userEntity
				.setFirstName(userDto.getFirstName())
				.setLastName(userDto.getLastName());
		this.userRepository.save(userEntity);
	}

	@Override
	public void updateUserDetail(Long userId, UserDto userDto) {
		Optional<User> byId = this.userRepository.findById(userId);
		if (!byId.isPresent()) {
			throw this.exceptionHandler.throwException(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(userId));
		}
		User existingUser = byId.get();
		if (userDto.getAuthorities() != null && !userDto.getAuthorities().isEmpty()) {
			existingUser.setAuthorities(String.join(",", userDto.getAuthorities()));
		}
		if (!StringUtils.isEmpty(userDto.getFirstName())) {
			existingUser.setFirstName(userDto.getFirstName());
		}
		if (!StringUtils.isEmpty(userDto.getLastName())) {
			existingUser.setLastName(userDto.getLastName());
		}
		existingUser.setActive(userDto.isActive());
		if (!StringUtils.isEmpty(userDto.getPassword())) {
			existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
		}
		this.userRepository.save(existingUser);
	}

	@Override
	public void updateUserRoles(UserDto userDto) {
		Assert.notNull(userDto, "userDao for updateUserRoles cannot be null");
		if (!this.userExists(userDto.getUserName())) {
			throw this.exceptionHandler.throwException(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getUserName());
		}
		User userEntity = this.userRepository.findByUserName(userDto.getUserName());
		userEntity
				.setAuthorities(String.join(",", userDto.getAuthorities()));
		this.userRepository.save(userEntity);
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {

		// TODO implement correctly
	}

	@Override
	public void changeUserPassword(String userName, String newPassword) {
		Assert.notNull(userName, "userName for changeUserPassword cannot be null");
		Assert.notNull(newPassword, "newPassword for changeUserPassword cannot be null");
		User userEntity = this.userRepository.findByUserName(userName);
		if (userEntity == null) {
			throw this.exceptionHandler.throwException(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userName);
		}
		userEntity.setPassword(this.passwordEncoder.encode(newPassword));
		this.userRepository.save(userEntity);
	}

	@Override
	public void deleteUser(String userName) {
		Assert.notNull(userName, "username should not be null");
		this.userRepository.deleteByUserName(userName);
	}

	@Override
	public void deleteUser(Long userId) {
		this.userRepository.deleteById(userId);
	}

	@Override
	public boolean userExists(String userName) {
		return !StringUtils.isEmpty(userName) && this.userRepository.existsByUserName(userName);
	}

	@Override
	public List<UserDto> getAllUsers() {
		return this.userRepository.findAll().stream().map(this::toUserDao).collect(Collectors.toList());
	}

	@Override
	public long totalUsersCount() {
		return this.userRepository.count();
	}

	@Override
	public long activeUsersCount() {
		return this.userRepository.countByActive(true);
	}

	@Override
	public long inactiveUserCount() {
		return this.userRepository.countByActive(false);
	}

	User toUser(UserDto userDto) {
		Assert.notNull(userDto, "userDao for toUser cannot be null");
		return User.builder()
				.userName(userDto.getUserName())
				.password(passwordEncoder.encode(userDto.getPassword()))
				.firstName(userDto.getFirstName())
				.lastName(userDto.getLastName())
				.authorities(String.join(",", userDto.getAuthorities()))
				.active(userDto.isActive())
				.build();
	}

	UserDto toUserDao(User user) {
		Assert.notNull(user, "user cannot be null");
		return UserDto.builder()
				.id(user.getId())
				.userName(user.getUserName())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.authorities(user.getAuthoritiesStream().collect(Collectors.toSet()))
				.active(user.isActive())
				.build();
	}
}