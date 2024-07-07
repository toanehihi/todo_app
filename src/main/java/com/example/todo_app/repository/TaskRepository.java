package com.example.todo_app.repository;

import com.example.todo_app.Common.Status;
import com.example.todo_app.Entity.Task;
import com.example.todo_app.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TaskRepository extends JpaRepository<Task,Integer> {
    public Task findTaskById(int id);

    public boolean existsTaskByTitle(String taskName);

    Integer countTaskByStatus(Status status);

    List<Task> findTasksByUser_Id(Integer id);

    List<Task> findTasksByUser(User userDetails);
}
