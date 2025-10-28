// File: core/src/main/java/com/coldchain/model/AuditLog.java
package com.coldchain.model;

import java.time.LocalDateTime;

public class AuditLog {
    private int auditId; // Corrected to int to match schema
    private Integer userId;
    private String actionType;
    private String details;
    private LocalDateTime timestamp;

    public AuditLog(Integer userId, String actionType, String details) {
        this.userId = userId;
        this.actionType = actionType;
        this.details = details;
    }

    // Getters and Setters for all fields...
    public int getAuditId() { return auditId; }
    public void setAuditId(int auditId) { this.auditId = auditId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}