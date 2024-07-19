package com.example.todo_app.controller;

import com.example.todo_app.common.Status;
import com.example.todo_app.entity.Task;
import com.example.todo_app.service.FilterTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/user/task")
public class FilterController {
    FilterTaskService filterTaskService;

    @Autowired
    public FilterController(FilterTaskService filterTaskService){
        this.filterTaskService=filterTaskService;
    }
    @GetMapping("/filterAmount")
    public Map<Status,Integer> filterAmountTask(){
        Map<Status,Integer> statusIntegerMap = filterTaskService.checkStatusTasks();
        return statusIntegerMap;
    }
    @GetMapping("/filterViaStatusAndPriority")
    public ResponseEntity<List<Task>> filterViaStatusAndPriority(@RequestParam("status") String status, @RequestParam("priority") String priority){
        List<Task> tasks = filterTaskService.filterTasks(status,priority);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }
}
