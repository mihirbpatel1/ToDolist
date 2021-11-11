package com.spring.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;

import com.spring.jump.model.Task;
import com.spring.jump.model.User;


public interface UserService {

	Optional<User> findById(Long id);

	List<User> getUsers();

	User addUser(User user);

	void deleteUser(User user);

	void deleteById(Long id);
	
	User updateUser(User user);
	
	List<Task> getTasksOfUser(Long userId);
	
	User saveUser (User newUser);

	AuthenticationManager authenticationManagerBean() throws Exception;


	 
}
