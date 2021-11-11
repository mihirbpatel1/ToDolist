package com.spring.jump.service;

import java.util.Optional;

import com.spring.jump.model.TaskDetail;
import com.spring.jump.model.User;


public interface TaskDetailService {

	Optional<TaskDetail> findById(Long taskDetailId);

	void deleteTaskDetail(TaskDetail taskDetail);

	void deleteById(Long taskDetailId);

	TaskDetail updateTaskDetail(TaskDetail taskDetail);

	User getUserOfTaskDetail(Long taskDetailId);
	
}
