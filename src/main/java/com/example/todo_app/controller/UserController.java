package com.example.todo_app.controller;

import com.example.todo_app.entity.Task;
import com.example.todo_app.entity.User;
import com.example.todo_app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    TaskService taskService;
    @Autowired
    public UserController(TaskService taskService){
        this.taskService=taskService;
    }
//    @GetMapping("/information")
//    public Authentication user(){
//        Authentication auth = SecurityContextHolder
//                .getContext()
//                .getAuthentication();
//        return auth;
//    }
    @GetMapping("/task")
    public ResponseEntity<List<Task>> findAllTasks(){
        return ResponseEntity.ok(taskService.getAllTasks());
    }
    @GetMapping("/task/{id}")
    public Integer findTaskById(@PathVariable("id") Integer id){
        Task tmpTask = taskService.getTaskById(id);
        return tmpTask.getPriority().ordinal();
    }

    @PostMapping("/add-task")
    public void addTask(@RequestBody Task task){
        taskService.addTask(task);
    }
    @PutMapping("/edit-task/{id}")
    public ResponseEntity<?> editTask(@RequestBody Task task, @PathVariable Integer id) {
        return ResponseEntity.ok(taskService.editTask(task,id));
    }
    @DeleteMapping("/delete-task/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.deleteTask(id));
    }

}
