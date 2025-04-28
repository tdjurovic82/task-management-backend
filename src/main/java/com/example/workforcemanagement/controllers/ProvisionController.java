package com.example.workforcemanagement.controllers;

import com.example.workforcemanagement.dto.ProvisioningRequestDTO;
import com.example.workforcemanagement.services.ProvisioningService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activate")
@CrossOrigin(origins = "http://localhost:4200")
public class ProvisionController {

    private final ProvisioningService provisioningService;

    public ProvisionController(ProvisioningService provisioningService) {
        this.provisioningService = provisioningService;
    }

    @PostMapping
    public ResponseEntity<String> activate(@RequestBody @Valid ProvisioningRequestDTO dto) {
        provisioningService.activate(dto);
        return ResponseEntity.ok("Provisioning successful.");
    }
    @GetMapping("/check")
    public ResponseEntity<Boolean> isActivated(@RequestParam Long uprn) {
        return ResponseEntity.ok(provisioningService.isActivated(uprn));
    }
}



