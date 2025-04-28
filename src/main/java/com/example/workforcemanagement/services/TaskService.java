package com.example.workforcemanagement.services;

import com.example.workforcemanagement.dto.CreateTaskDTO;
import com.example.workforcemanagement.dto.TaskDTO;
import com.example.workforcemanagement.dto.TaskDetailsDTO;
import com.example.workforcemanagement.entities.Location;
import com.example.workforcemanagement.entities.Task;
import com.example.workforcemanagement.entities.User;
import com.example.workforcemanagement.entities.enums.TaskStatus;
import com.example.workforcemanagement.exceptions.TaskException;
import com.example.workforcemanagement.exceptions.UserException;
import com.example.workforcemanagement.repositories.LocationRepository;
import com.example.workforcemanagement.repositories.TaskRepository;
import com.example.workforcemanagement.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private LocationRepository locationRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, LocationRepository locationRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

//    public List<Task> getAllTasks() {
//
//        return taskRepository.findAll().stream().map()
//    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    public TaskDTO getTaskDTOById(Long id) {

        return mapToResponse(taskRepository.findById(id).orElseThrow(() -> new TaskException("Task not found")));
    }

//    public List<Task> createTask(List<Task> tasks) {
//
//        return taskRepository.saveAll(tasks);
//    } bez dto
    public TaskDTO createTask(CreateTaskDTO taskDTO) {



        Location location = locationRepository.findById(taskDTO.getUprn()).orElseThrow(
                ()->new TaskException("Location for this task was not found"));

        Task task = mapToEntity(taskDTO, location);
        task = taskRepository.save(task);

        String cleanPostcode = location.getPostcode() != null ? location.getPostcode().replaceAll("\\s+", "") : "UNKNOWN";
        task.setDescription(task.getId() + "_" + cleanPostcode + "_" + task.getTaskType());

        return mapToResponse(taskRepository.save(task));



    }




    public TaskDTO assignTask(Long taskId, Long userId) {

        Task task = taskRepository.findById(taskId).orElseThrow(()->new TaskException("Task not found"));



        if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
            task.setAssignedUser(user);
            System.out.print("Assigning task " + task.getDescription() + " to user " + user.getName());
        } else {
            task.setAssignedUser(null);
        }
        return mapToResponse(taskRepository.save(task));


    }


public TaskDTO patchTask(Long taskId, TaskDTO dto) {
    Task task = taskRepository.findById(taskId).orElseThrow(()->new TaskException("Task not found"));



    if (dto.getDescription() != null) {
        task.setDescription(dto.getDescription());
    }
    if (dto.getUprn() != null) {
        task.setLocation(locationRepository.findById(dto.getUprn()).orElseThrow(()->new TaskException("Location for this task was not found")));
    }
    if (dto.getUserId() != null) {
        task.setAssignedUser(userRepository.findById(dto.getUserId()).orElseThrow(()->new TaskException("User not found")));
    }
    if (dto.getTaskType() != null) {
        task.setTaskType(dto.getTaskType());
    }
    if (dto.getStatus() != null) {
        task.setStatus(dto.getStatus());
    }



    return mapToResponse(taskRepository.save(task));
}

    public Map<String, Long> getTaskStatusSummary() {
        List<Task> allTasks = taskRepository.findAll();
        Map<String, Long> summary = new HashMap<>();

        for (Task task : allTasks) {
            String status = task.getStatus().name();
            summary.put(status, summary.getOrDefault(status, 0L) + 1);
        }

        return summary;
    }

    public List<TaskDTO> findTaskByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    public TaskDetailsDTO getTaskDetailsDTOById(Long id) {

        Task task = taskRepository.findById(id).orElseThrow(()->new TaskException("Task not found"));
        User user;
        if (task.getAssignedUser() != null) {
            user = userRepository.findById(task.getAssignedUser().getId()).orElseThrow(() -> new UserException("User not found"));
        } else {
            user = null;
        }
        Location location = locationRepository.findByUprn(task.getLocation().getUprn());

        return mapToTaskDetailsDTO(task, user, location);


    }

    public Task mapToEntity(CreateTaskDTO taskDTO, Location location) {

        User user = null;

        if (taskDTO.getUserId() != null) {

            user = userRepository.findById(taskDTO.getUserId()).orElseThrow(()->new UserException("User not found"));

        }

        TaskStatus status = (taskDTO.getStatus() != null) ? taskDTO.getStatus() : TaskStatus.OPEN;



        return Task.builder()
                .taskType(taskDTO.getTaskType())
                .location(location)
                .assignedUser(user)
                .status(status)
                .build();

    }

    public TaskDTO mapToResponse(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .description(task.getDescription())
                .taskType(task.getTaskType())
                .status(task.getStatus())
                .userId(task.getAssignedUser() != null ? task.getAssignedUser().getId() : null)
                .uprn((task.getLocation()==null)?null:(task.getLocation().getUprn()))
                .build();
    }

    public TaskDetailsDTO mapToTaskDetailsDTO(Task task, User user, Location location) {
        return TaskDetailsDTO.builder()
                .id(task.getId())
                .description(task.getDescription())
                .taskType(task.getTaskType())
                .status(task.getStatus())
                .assignee(user != null ? user.getName() : "Unassigned")
                .address(location.getAddress())
                .postcode(location.getPostcode())
                .uprn(location.getUprn())
                .build();
    }









}
