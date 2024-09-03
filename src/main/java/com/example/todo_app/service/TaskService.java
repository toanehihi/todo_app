package com.example.todo_app.service;

import com.example.todo_app.entity.Task;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    List<Task> getAllTasksCurrentUser();
    void addTask(Task task);
    Task getTaskById(Integer id);
    Task editTask(Task task, Integer id);
    Task deleteTask(Integer id);
    List<Task> getTasksByUser();
}
