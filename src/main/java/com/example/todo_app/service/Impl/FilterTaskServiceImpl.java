package com.example.todo_app.service.Impl;

import com.example.todo_app.common.Priority;
import com.example.todo_app.common.Status;
import com.example.todo_app.entity.Task;
import com.example.todo_app.repository.TaskRepository;
import com.example.todo_app.service.FilterTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;
@Service
public class FilterTaskServiceImpl implements FilterTaskService {
    TaskRepository taskRepository;

    @Autowired
    public FilterTaskServiceImpl(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }
    @Override
    public Map<Status,Integer> checkStatusTasks(){
        Map<Status, Integer> tmpMap = new HashMap<>();
        tmpMap.put(Status.In_Progress,taskRepository.countTaskByStatus(Status.In_Progress));
        tmpMap.put(Status.Completed,taskRepository.countTaskByStatus(Status.Completed));
        tmpMap.put(Status.Cancelled,taskRepository.countTaskByStatus(Status.Cancelled));
        tmpMap.put(Status.Overdue,taskRepository.countTaskByStatus(Status.Overdue));
        return tmpMap;
    }

    @Override
    public List<Task> filterTasks(String status,String priority){
        List<Task> tasks = taskRepository.findAll();
        Stream<Task> filterResult = tasks.stream();

        if(!status.equals("Default")){
            filterResult = filterResult.filter(tmp->tmp.getStatus().equals(Status.valueOf(status)));
        }
        if(!priority.equals("Default")){
            filterResult = filterResult.filter(tmp->tmp.getPriority().equals(Priority.valueOf(priority)));
        }
        return filterResult.toList();
    }

}
