package com.swedbank.StudentApplication.group;

import com.swedbank.StudentApplication.person.Person;
import com.swedbank.StudentApplication.person.exception.PersonNotFoundException;
import com.swedbank.StudentApplication.task.Task;
import com.swedbank.StudentApplication.task.TaskService;
import com.swedbank.StudentApplication.task.exceptiion.TaskNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/groups")
public class GroupController {

    private final GroupService service;
    private final TaskService taskService;

    public GroupController(GroupService service, TaskService taskService){
        this.service = service;
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups(){
        List<Group> groups = service.findAll();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Group> getGroupByid(@PathVariable final long id){
        Group group  = service.findById(id);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody final Group group){
        Group savedGroup = service.save(group);
        return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<Group> updateGroup(@RequestBody final Group group){
        Group updateGroup = service.update(group);
        return new ResponseEntity<>(updateGroup, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable final long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllGroups(){
        service.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{tid}/addTask/{id}")
    public ResponseEntity<?> addTaskToGroup(@PathVariable long tid, @PathVariable long id) throws TaskNotFoundException {
        Task task = taskService.findById(tid);
        Group group = service.findById(id);

        Set<Task> tasks = group.getTasks();
        tasks.add(task);
        task.setGroup(group);
        service.saveAndFlush(group);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{tid}/removeTask/{id}")
    public ResponseEntity<?> removeTaskFromGroup(@PathVariable long tid, @PathVariable long id) throws TaskNotFoundException {
        Task task = taskService.findById(tid);
        Group group = service.findById(id);

        Set<Task> tasks = group.getTasks();

        if (tasks.contains(task)) {
            tasks.remove(task);
            task.setGroup(group);
            service.saveAndFlush(group);
        }
        return ResponseEntity.ok().build();
    }
}
