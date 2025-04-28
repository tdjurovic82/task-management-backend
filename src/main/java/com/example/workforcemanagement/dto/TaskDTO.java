package com.example.workforcemanagement.dto;

import com.example.workforcemanagement.entities.enums.TaskType;
import com.example.workforcemanagement.entities.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDTO {

    private Long id;
    //private String title;
    private String description;
    private TaskType taskType;
    private TaskStatus status;
    private Long userId;
    private Long uprn;
}
