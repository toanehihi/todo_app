package com.example.todo_app.service;

import com.example.todo_app.common.Status;
import com.example.todo_app.entity.Task;

import java.util.Map;
import java.util.List;
public interface FilterTaskService {
    Map<Status, Integer> checkStatusTasks();

    List<Task> filterTasks(String status, String priority);
}
