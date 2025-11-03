package com.coldchain.controller;

import com.coldchain.model.Alert;
import com.coldchain.repository.AlertRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AlertController {

    private final AlertRepository alertRepository;

    public AlertController(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    // GET /api/alerts
    @GetMapping("/alerts")
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    // GET /api/alerts/1
    @GetMapping("/alerts/{id}")
    public ResponseEntity<Alert> getAlertById(@PathVariable Long id) {
        Optional<Alert> alert = alertRepository.findById(id);
        return alert.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }
}