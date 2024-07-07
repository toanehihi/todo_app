package com.example.todo_app.Service;
import com.example.todo_app.Entity.Task;

import java.util.List;
public interface SortingService {
    List<Task> sortListTasksViaPriority();
}
