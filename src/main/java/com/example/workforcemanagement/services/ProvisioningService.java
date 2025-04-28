package com.example.workforcemanagement.services;

import com.example.workforcemanagement.dto.ProvisioningRequestDTO;
import com.example.workforcemanagement.entities.OLT;
import com.example.workforcemanagement.entities.ONT;
import com.example.workforcemanagement.entities.Task;
import com.example.workforcemanagement.entities.enums.TaskStatus;
import com.example.workforcemanagement.exceptions.ProvisioningException;
import com.example.workforcemanagement.repositories.OLTRepository;
import com.example.workforcemanagement.repositories.ONTRepository;
import com.example.workforcemanagement.repositories.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class ProvisioningService {

    private final TaskRepository taskRepository;
    private final OLTRepository oltRepository;
    private final ONTRepository ontRepository;


    public ProvisioningService(TaskRepository taskRepository, OLTRepository oltRepository, ONTRepository ontRepository) {
        this.taskRepository = taskRepository;
        this.oltRepository = oltRepository;
        this.ontRepository = ontRepository;

    }

    public void activate(ProvisioningRequestDTO dto) {
        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new ProvisioningException("Task not found"));

        Long uprn = task.getLocation().getUprn();

        if (ontRepository.existsByUprn(task.getLocation().getUprn())) {
            throw new ProvisioningException("Customer is already provisioned at this location.");
        }

        OLT olt = oltRepository.findByOltName(dto.getOltName())
                .orElseThrow(() -> new ProvisioningException("OLT not found"));

        if (!olt.getUprns().contains(uprn)) {
            throw new ProvisioningException("OLT does not serve this UPRN");
        }

        if (ontRepository.existsByUprn(uprn)) {
            throw new ProvisioningException("ONT already provisioned");
        }


        ONT ont = ONT.builder()
                .serialNumber(dto.getSerialNumber())
                .port(dto.getPort())
                .uprn(uprn)
                .packageName(dto.getPackageName())
                .build();

        task.setStatus(TaskStatus.DONE);
        taskRepository.save(task);
        ontRepository.save(ont);

    }

    public boolean isActivated(Long uprn) {
        return ontRepository.existsByUprn(uprn);
    }
}