package com.example.todo_app.service;
import com.example.todo_app.entity.Task;

import java.util.List;
public interface SortingService {
    List<Task> sortListTasksViaPriority();
}
