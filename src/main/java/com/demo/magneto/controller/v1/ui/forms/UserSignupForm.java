package com.demo.magneto.controller.v1.ui.forms;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserSignupForm {

	@Length(min = 4, max = 30)
	@NotEmpty(message = "username cannot be empty")
	@Email(message = "should a valid email address")
	protected String userName;

	@NotEmpty(message = "password cannot be empty")
	@Length(min = 5, max = 30, message = "password should be between 5 and 30 characters long")
	protected String password;

	@NotEmpty(message = "please specify your first name")
	protected String firstName;

	@NotEmpty(message = "please specify your last name")
	protected String lastName;
}
