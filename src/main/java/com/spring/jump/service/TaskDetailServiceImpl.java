package com.spring.jump.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.jump.model.TaskDetail;
import com.spring.jump.model.User;
import com.spring.jump.repository.TaskDetailRepository;


@Service
public class TaskDetailServiceImpl implements TaskDetailService {

	@Autowired
	private TaskDetailRepository taskDetailRepository;


	@Override
	public void deleteTaskDetail(TaskDetail taskDetail) {
		taskDetailRepository.delete(taskDetail);
	}

	@Override
	public void deleteById(Long id) {
		taskDetailRepository.deleteById(id);
	}

	@Override
	public Optional<TaskDetail> findById(Long taskId) {
		return taskDetailRepository.findById(taskId);
	}


	@Override
	public User getUserOfTaskDetail(Long taskDetailId) {
		
		Optional<TaskDetail> taskDetail = taskDetailRepository.findById(taskDetailId);
		if(taskDetail.isPresent()) {
			User user = taskDetail.get().getTask().getUser();
			return user;
		}
		return null;
	}

	@Override
	public TaskDetail updateTaskDetail(TaskDetail taskDetail) {
		return taskDetailRepository.save(taskDetail);
	}




}
