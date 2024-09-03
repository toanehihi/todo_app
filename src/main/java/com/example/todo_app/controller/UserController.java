package com.example.todo_app.controller;

import com.example.todo_app.configuration.JwtAuthenticationFilter;
import com.example.todo_app.configuration.JwtService;
import com.example.todo_app.entity.Task;
import com.example.todo_app.entity.User;
import com.example.todo_app.repository.TaskRepository;
import com.example.todo_app.repository.UserRepository;
import com.example.todo_app.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final TaskService taskService;



    private final JwtService jwtService;

    //test
    @GetMapping("/information")
    public ResponseEntity getUserInformation() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new SecurityException("User is not authenticated!");
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return ResponseEntity.ok(userDetails);

    }
    @GetMapping("/task")
    public ResponseEntity<List<Task>> findAllTasks(){

        return ResponseEntity.ok(taskService.getTasksByUser());
    }
    @GetMapping("/task/{id}")
    public Integer findTaskById(@PathVariable("id") Integer id){
        Task tmpTask = taskService.getTaskById(id);
        return tmpTask.getPriority().ordinal();
    }



    @PostMapping("/add-task")
    public void addTask(@RequestBody Task task, Principal principal){
        taskService.addTask(task);
        System.out.println("Completed");
    }



    @PutMapping("/edit-task/{id}")
    public ResponseEntity<?> editTask(@RequestBody Task task, @PathVariable Integer id) {
        return ResponseEntity.ok(taskService.editTask(task,id));
    }
    @DeleteMapping("/delete-task/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.deleteTask(id));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(String token){
        return ResponseEntity.ok(jwtService.extractAllClaims(token));
    }


}
