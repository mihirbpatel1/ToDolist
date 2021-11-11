package com.spring.jump.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.spring.jump.model.User;



@Component
public class ValidationUtil {

	public void validateUser(User user) {

		Assert.notNull(user.getName(), "User name must not be null");
		Assert.notNull(user.getSurname(), "User surname must not be null");
		Assert.notNull(user.getUserType(), "User type must not be null");

	}
}