package com.example.todo_app.service;

import com.example.todo_app.entity.Task;

import java.util.List;
public interface TaskService {
    List<Task> getAllTasks();
    void addTask(Task task);
    Task getTaskById(Integer id);
    Task editTask(Task task, Integer id);
    Task deleteTask(Integer id);
}
