package com.example.todo_app.service.Impl;

import com.example.todo_app.entity.Task;
import com.example.todo_app.entity.User;
import com.example.todo_app.repository.TaskRepository;
import com.example.todo_app.repository.UserRepository;
import com.example.todo_app.service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,UserRepository userRepository){
        this.taskRepository=taskRepository;
        this.userRepository=userRepository;
    }
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
    public void addTask(Task task){
//        //get information current user
//        User userDetails = (User) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
//        //find user if exist
//        User user = userRepository.findUserByEmail(userDetails.getEmail())
//                .orElseThrow(()->new RuntimeException("User not found!"));
//        //assign user to this task
//        task.setUser(user);
        //save to db
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
