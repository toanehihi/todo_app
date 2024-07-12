package com.example.todo_app.repository;

import com.example.todo_app.common.Status;
import com.example.todo_app.entity.Task;
import com.example.todo_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
    public Task findTaskById(int id);

    public boolean existsTaskByTitle(String taskName);

    Integer countTaskByStatus(Status status);

    List<Task> findTasksByUser_Id(Integer id);

    List<Task> findTasksByUser(User userDetails);
}
