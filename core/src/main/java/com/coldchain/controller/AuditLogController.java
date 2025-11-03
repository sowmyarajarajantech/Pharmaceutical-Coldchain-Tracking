package com.coldchain.controller;

import com.coldchain.model.AuditLog;
import com.coldchain.repository.AuditLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuditLogController {

    private final AuditLogRepository auditLogRepository;

    public AuditLogController(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // GET /api/auditlogs
    @GetMapping("/auditlogs")
    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAll();
    }

    // GET /api/auditlogs/1
    @GetMapping("/auditlogs/{id}")
    public ResponseEntity<AuditLog> getAuditLogById(@PathVariable Long id) {
        Optional<AuditLog> log = auditLogRepository.findById(id);
        return log.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }
}