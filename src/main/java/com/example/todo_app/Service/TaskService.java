package com.example.todo_app.Service;

import com.example.todo_app.Entity.Task;

import java.util.List;
public interface TaskService {
    List<Task> getAllTasks();
    void addTask(Task theTask);
    Task getTaskById(Integer id);
    Task editTask(Task theTask, Integer theId);
    Task deleteTask(Integer theId);
}
