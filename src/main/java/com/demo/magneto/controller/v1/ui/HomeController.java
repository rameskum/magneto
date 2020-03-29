package com.demo.magneto.controller.v1.ui;

import com.demo.magneto.controller.v1.ui.forms.UserSignupForm;
import com.demo.magneto.dto.UserDto;
import com.demo.magneto.service.UserService;
import com.google.common.collect.Sets;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static com.demo.magneto.security.ApplicationUserRoles.USER;

@Controller
public class HomeController {

	private UserService userService;

	public HomeController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = {"/login"})
	public ModelAndView loginPage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return new ModelAndView("redirect:/dashboard");
		}
		return new ModelAndView("login");
	}

	@GetMapping(value = "/signup")
	public ModelAndView signup() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			/* The user is logged in :) */
			return new ModelAndView("redirect:/dashboard");
		}

		ModelAndView modelAndView = new ModelAndView("signup");
		modelAndView.addObject("userSignupFormData", new UserSignupForm());
		return modelAndView;
	}

	@PostMapping("/signup")
	public ModelAndView signup(@Valid @ModelAttribute("userSignupFormData") UserSignupForm userSignupForm, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("signup");
		if (bindingResult.hasErrors()) {
			return modelAndView;
		} else {
			try {
				UserDto userDto = UserDto.builder()
						.userName(userSignupForm.getUserName().toLowerCase())
						.password(userSignupForm.getPassword())
						.firstName(userSignupForm.getFirstName())
						.lastName(userSignupForm.getLastName())
						.authorities(Sets.newHashSet(USER.getGrantedAuthorities()))
						.build();
				this.userService.createUser(userDto);
				return new ModelAndView("redirect:/login?registered");
			} catch (Exception ex) {
				bindingResult.rejectValue("userName", ex.getMessage());
			}
			return modelAndView;
		}
	}
}
