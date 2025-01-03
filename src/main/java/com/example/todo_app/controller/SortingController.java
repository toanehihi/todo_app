package com.example.todo_app.controller;

import com.example.todo_app.entity.Task;
import com.example.todo_app.service.SortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
@RestController
@RequestMapping("/api/v1/user/task")
public class SortingController {
    private SortingService sortingService;

    @Autowired
    public SortingController(SortingService sortingService){
        this.sortingService=sortingService;
    }

    @GetMapping("/sorting")
    public List<Task> sortingTasksViaPriority(){
        return sortingService.sortListTasksViaPriority();
    }
}
