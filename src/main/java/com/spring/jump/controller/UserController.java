package com.spring.jump.controller;

import static com.spring.jump.security.SecurityConstants.TOKEN_PREFIX;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.jump.model.User;
import com.spring.jump.security.JwtTokenProvider;
import com.spring.jump.service.MapValidationErrorService;
import com.spring.jump.service.UserService;
import com.spring.jump.util.JWTLoginSucessReponse;
import com.spring.jump.util.LoginRequest;
import com.spring.jump.validator.LoginRequestValidator;
import com.spring.jump.validator.UserValidator;



@RestController
@RequestMapping("/api/users")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private LoginRequestValidator loginRequestValidator;



	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {


		loginRequestValidator.validate(loginRequest, result);

		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if (errorMap != null)
			return errorMap;

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

		logger.info("login returning");
		return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {


		userValidator.validate(user, result);

		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);

		if (errorMap != null)
			return errorMap;

		

		User newUser = userService.saveUser(user);

		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}

	@GetMapping("/getUsers")
	public @ResponseBody List<User> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("/users/{id}")
	public User retrieveUser(@RequestParam Long userId) {

		Optional<User> user = userService.findById(userId);
		return user.isPresent()?user.get():null;
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@RequestParam Long id) {
		userService.deleteById(id);
	}

	@PutMapping("/users/{id}")
	public void updateUser(@Valid @RequestBody User userDetails) {

		Optional<User> user = userService.findById(userDetails.getId());
			
		if (user.isPresent() && user.get().getUsername().equals(userDetails.getUsername()))
			userService.addUser(userDetails);
	}

}
