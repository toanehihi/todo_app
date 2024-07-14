package com.example.todo_app.service.Impl;

import com.example.todo_app.entity.Task;
import com.example.todo_app.repository.TaskRepository;
import com.example.todo_app.service.SortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SortingServiceImpl implements SortingService {
    TaskRepository taskRepository;
    @Autowired
    public SortingServiceImpl(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }
    //sort task
    @Override
    public List<Task> sortListTasksViaPriority(){
        List<Task> tasks = taskRepository.findAll();
        tasks.sort((task,t1)->task.getPriority().compareTo(t1.getPriority()));
        return tasks;
    }

}
