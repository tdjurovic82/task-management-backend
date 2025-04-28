package com.example.workforcemanagement.repositories;

import com.example.workforcemanagement.entities.Task;
import com.example.workforcemanagement.entities.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
        List<Task> findByStatus(TaskStatus status);
    }

