package com.spring.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.jump.exception.UsernameAlreadyExistsException;
import com.spring.jump.model.Task;
import com.spring.jump.model.User;
import com.spring.jump.repository.UserRepository;
import com.spring.jump.validator.ValidationUtil;




@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ValidationUtil validationUtil;


	@Bean
	public PasswordEncoder passwordEncoder()
	{
	    return new BCryptPasswordEncoder();
	}

    public User saveUser (User newUser){
        try{
            newUser.setPassword(passwordEncoder().encode(newUser.getPassword()));
            //Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);

        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists");
        }
    }
    
	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public User addUser(User user) {
		validationUtil.validateUser(user);
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public Optional<User> findById(Long id) {

		return userRepository.findById(id);
	}

	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<Task> getTasksOfUser(Long userId) {

		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return user.get().getTasks();
		}
		return null;
	}

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
