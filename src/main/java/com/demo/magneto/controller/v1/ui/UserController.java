package com.demo.magneto.controller.v1.ui;

import com.demo.magneto.controller.v1.ui.forms.UserCreationForm;
import com.demo.magneto.controller.v1.ui.forms.UserUpdateFrom;
import com.demo.magneto.dto.UserDto;
import com.demo.magneto.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/users")
@Controller
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = {"/", ""})
	public ModelAndView getAllUsers() {
		ModelAndView usersModel = new ModelAndView("users");
		try {
			List<UserDto> allUsers = userService.getAllUsers();
			usersModel.addObject("users", allUsers);
			usersModel.addObject("activeUserCount", userService.activeUsersCount());
			usersModel.addObject("inactiveUserCount", userService.inactiveUserCount());
		} catch (Exception ex) {
			usersModel.addObject("error", ex.getMessage());
			usersModel.addObject("users", Collections.singleton(UserDto.builder().build()));
		}
		return usersModel;
	}

	@PostMapping("/create")
	public ModelAndView createUser(@Valid UserCreationForm userCreationForm, BindingResult bindingResult) {
		ModelAndView userModel = new ModelAndView("redirect:/users");
		if (bindingResult.hasErrors()) {
			userModel.addObject("error", bindingResult.getAllErrors().stream().map(object -> {
				if (object instanceof FieldError) {
					FieldError fieldError = (FieldError) object;
					return fieldError.getField() + " -> " + fieldError.getDefaultMessage();
				}
				return "";
			}).collect(Collectors.joining("\n")));
		} else {
			try {
				UserDto userDto = UserDto.builder()
						.userName(userCreationForm.getUserName().toLowerCase())
						.password(userCreationForm.getPassword())
						.firstName(userCreationForm.getFirstName())
						.lastName(userCreationForm.getLastName())
						.active(userCreationForm.isActive())
						.authorities(userCreationForm.getPermissions())
						.build();
				this.userService.createUser(userDto);
				userModel.addObject("success", String.format("User {%s} created successfully", userDto.getUserName().toLowerCase()));
			} catch (Exception ex) {
				userModel.addObject("error", ex.getMessage());
			}
		}
		return userModel;
	}

	@GetMapping("/{userId}")
	public ModelAndView getUserInfo(@PathVariable("userId") final Long userId) {
		ModelAndView userModel = new ModelAndView("users");
		UserDto userDto = this.userService.findUserById(userId);
		if (userDto == null) {
			userDto = UserDto.builder().build();
		}
		userModel.addObject("user", userDto);
		return userModel;
	}

	@PostMapping("/{userId}/update")
	public ModelAndView updateUser(@PathVariable("userId") final Long userId, UserUpdateFrom userUpdateFrom) {
		userUpdateFrom.getPerm().forEach(per -> userUpdateFrom.getPermissions().add(per));
		UserDto userDto = UserDto.builder()
				.userName(userUpdateFrom.getUserName().toLowerCase())
				.password(userUpdateFrom.getPassword())
				.firstName(userUpdateFrom.getFirstName())
				.lastName(userUpdateFrom.getLastName())
				.active(userUpdateFrom.isActive())
				.authorities(userUpdateFrom.getPermissions())
				.build();
		this.userService.updateUserDetail(userId, userDto);
		ModelAndView view = new ModelAndView("redirect:/users/" + userId);
		view.addObject("success", String.format("User {%s} has been updated", userId));
		return view;
	}

	@PostMapping("/{userId}/delete")
	public ModelAndView deleteUser(@PathVariable("userId") final Long userId) {
		ModelAndView modelAndView = new ModelAndView("redirect:/users");
		this.userService.deleteUser(userId);

		modelAndView.addObject("success", "user deleted successfully");
		return modelAndView;
	}
}
