package com.spring.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.jump.model.TaskDetail;

public interface TaskDetailRepository extends JpaRepository<TaskDetail, Long> {
}