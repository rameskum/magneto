package com.demo.magneto.controller.v1.ui.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserUpdateFrom extends UserSignupForm {

	protected boolean active;

	protected Set<String> permissions = new HashSet<>();
	protected List<String> perm = new ArrayList<>();
}
