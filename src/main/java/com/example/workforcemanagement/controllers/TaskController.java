package com.example.workforcemanagement.controllers;


import com.example.workforcemanagement.dto.AssignRequest;
import com.example.workforcemanagement.dto.CreateTaskDTO;
import com.example.workforcemanagement.dto.TaskDTO;
import com.example.workforcemanagement.dto.TaskDetailsDTO;
import com.example.workforcemanagement.entities.enums.TaskStatus;
import com.example.workforcemanagement.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/tasks")
public class TaskController {


    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(@RequestParam(required = false) String filter) {
        if (filter != null) {

            return ResponseEntity.ok(taskService.findTaskByStatus(TaskStatus.valueOf(filter.toUpperCase())));
        }

        return ResponseEntity.ok(taskService.getAllTasks());
    }
    @PostMapping("/bulk")
    public List<TaskDTO> createTasksBulk(@RequestBody List<CreateTaskDTO> taskDTOs) {
        return taskDTOs.stream()
                .map(taskService::createTask)
                .collect(Collectors.toList());
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<TaskDetailsDTO> getTaskDetails(@PathVariable Long id) {

        return ResponseEntity.ok(taskService.getTaskDetailsDTOById(id));
    }


    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long id) {
        TaskDTO dto = taskService.getTaskDTOById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid CreateTaskDTO createTaskDTO) {

       return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(createTaskDTO));

    }

    @PutMapping("/assign")
    public ResponseEntity<TaskDTO> assignTask(@RequestBody AssignRequest assignment) {
        TaskDTO updated = taskService.assignTask(assignment.getTaskID(), assignment.getUserID());
        return ResponseEntity.ok(updated);
    }



    @GetMapping("/task-summary")
    public Map<String, Long> getStatusSummary() {
        return taskService.getTaskStatusSummary();
    }

    @PatchMapping("/{taskID}")
    public ResponseEntity<TaskDTO> patchTask(@PathVariable long taskID, @RequestBody TaskDTO dto) {
        return ResponseEntity.ok(taskService.patchTask(taskID,dto));
    }



}
