package com.coldchain.controller;

import com.coldchain.model.TemperatureLog;
import com.coldchain.repository.TemperatureLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TemperatureLogController {

    private final TemperatureLogRepository tempLogRepository;

    public TemperatureLogController(TemperatureLogRepository tempLogRepository) {
        this.tempLogRepository = tempLogRepository;
    }

    // GET /api/temperaturelogs
    @GetMapping("/temperaturelogs")
    public List<TemperatureLog> getAllTemperatureLogs() {
        return tempLogRepository.findAll();
    }

    // GET /api/temperaturelogs/1
    @GetMapping("/temperaturelogs/{id}")
    public ResponseEntity<TemperatureLog> getTemperatureLogById(@PathVariable Long id) {
        Optional<TemperatureLog> log = tempLogRepository.findById(id);
        return log.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }
}