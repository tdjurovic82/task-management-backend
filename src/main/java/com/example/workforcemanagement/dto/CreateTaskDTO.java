package com.example.workforcemanagement.dto;

import com.example.workforcemanagement.entities.enums.TaskType;
import com.example.workforcemanagement.entities.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTaskDTO {



    //private String description;
    private TaskStatus status=TaskStatus.OPEN;
    @NotNull(message ="Task type must be defined")
    private TaskType taskType;
    private Long userId;
    @NotNull(message="Location parameter missing")
    private Long uprn;


}
