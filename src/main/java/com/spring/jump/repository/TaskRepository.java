package com.spring.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.jump.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}