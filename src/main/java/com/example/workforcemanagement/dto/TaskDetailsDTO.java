package com.example.workforcemanagement.dto;


import com.example.workforcemanagement.entities.enums.TaskStatus;
import com.example.workforcemanagement.entities.enums.TaskType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDetailsDTO {

    private Long id;

    //private String title;
     private String description;
    private TaskStatus status;
    private TaskType taskType;
    private String assignee;
    private String postcode;
    private String address;
    private Long uprn;
}
