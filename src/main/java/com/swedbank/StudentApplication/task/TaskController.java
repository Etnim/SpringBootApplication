package com.swedbank.StudentApplication.task;

import com.swedbank.StudentApplication.group.Group;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PatchMapping(value = "update")
    public ResponseEntity<Task> updateTask(@RequestBody final Task task){
        Task updateTask = service.update(task);
        return new ResponseEntity<>(updateTask, HttpStatus.OK);
    }
    @PostMapping(value="create")
    public ResponseEntity<Task> createTask(@RequestBody final Task task){
        Task saveTask = service.save(task);
        return new ResponseEntity<>(saveTask, HttpStatus.CREATED);
    }
}
