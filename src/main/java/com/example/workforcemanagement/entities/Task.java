package com.example.workforcemanagement.entities;
import com.example.workforcemanagement.entities.enums.TaskStatus;
import com.example.workforcemanagement.entities.enums.TaskType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Task {
    @Id
    @GeneratedValue
    private Long id;

    //private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.OPEN;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @ManyToOne
    @JoinColumn(name="assigned_user_id")
    private User assignedUser;

    @ManyToOne
    @JoinColumn(name="uprn")
    private Location location;


}
