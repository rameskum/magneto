package com.demo.magneto.controller.v1.ui.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserCreationForm extends UserSignupForm {

	protected boolean active;

	protected Set<String> permissions = new HashSet<>();
}
