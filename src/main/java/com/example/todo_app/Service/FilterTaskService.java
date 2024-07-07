package com.example.todo_app.Service;

import com.example.todo_app.Common.Status;
import com.example.todo_app.Entity.Task;

import java.util.Map;
import java.util.List;
public interface FilterTaskService {
    Map<Status, Integer> checkStatusTasks();

    List<Task> filterTasks(String status, String priority);
}
