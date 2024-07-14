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
    //check status task
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
        //put all task to List
        List<Task> tasks = taskRepository.findAll();
        //transfer List into Stream
        Stream<Task> filterResult = tasks.stream();
        //filter by Status, if != Default -> check them which task.Status == status, keep it, then remove the rest from the stream
        if(!status.equals("Default")){
            filterResult = filterResult.filter(tmp->tmp.getStatus().equals(Status.valueOf(status)));
        }
        //filter by Priority, if exist -> keep all task has task.Priority == priority and remove the rest from the stream
        if(!priority.equals("Default")){
            filterResult = filterResult.filter(tmp->tmp.getPriority().equals(Priority.valueOf(priority)));
        }
        return filterResult.toList();
    }

}
