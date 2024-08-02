package com.example.todo_app.service.Impl;

import com.example.todo_app.entity.Task;
import com.example.todo_app.entity.User;
import com.example.todo_app.repository.TaskRepository;
import com.example.todo_app.repository.UserRepository;
import com.example.todo_app.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.util.List;

import static java.rmi.server.LogStream.log;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final View error;
    private final UserDetailsService userDetailsService;


    @Override
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Integer id){
        return taskRepository.findTaskById(id);
    }

    @Override
    @Transactional
    public void addTask(Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User is not authenticated!");
        }

        Object principal = authentication.getPrincipal();

        UserDetails userDetails;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        } else {
            throw new SecurityException("Authentication principal is not an instance of UserDetails!");
        }

        User user = userRepository.findUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        task.setUser(user);
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task deleteTask(Integer id){
        Task task = taskRepository.findTaskById(id);
        taskRepository.deleteById(id);
        return task;
    }

    @Override
    @Transactional
    public Task editTask(Task task,Integer id){
        //create a temp task
        Task tmpTask = taskRepository.findTaskById(id);
        //set information for tmp task
        tmpTask.setNote(task.getNote());
        tmpTask.setTitle(task.getTitle());
        tmpTask.setPriority(task.getPriority());
        tmpTask.setStatus(task.getStatus());
        return tmpTask;
    }
}
