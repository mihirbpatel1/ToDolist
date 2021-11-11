package com.spring.jump.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.jump.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAll();

	Optional<User> findByUsername(String username);

	Optional<User> findById(Long id);

	Optional<User> findByName(String name);

}
