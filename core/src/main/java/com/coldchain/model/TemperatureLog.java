// File: core/src/main/java/com/coldchain/model/TemperatureLog.java
package com.coldchain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TemperatureLog {
    private int logId; // Corrected to int to match schema
    private int shipmentId;
    private BigDecimal temperature; // Corrected from temp_recorded
    private LocalDateTime recordedAt;
    private int recordedByUserId;
    private LocalDateTime createdAt;

    public TemperatureLog(int shipmentId, BigDecimal temperature, LocalDateTime recordedAt, int recordedByUserId) {
        this.shipmentId = shipmentId;
        this.temperature = temperature;
        this.recordedAt = recordedAt;
        this.recordedByUserId = recordedByUserId;
    }

    // Getters and Setters for all fields...
    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }
    public int getShipmentId() { return shipmentId; }
    public void setShipmentId(int shipmentId) { this.shipmentId = shipmentId; }
    public BigDecimal getTemperature() { return temperature; }
    public void setTemperature(BigDecimal temperature) { this.temperature = temperature; }
    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
    public int getRecordedByUserId() { return recordedByUserId; }
    public void setRecordedByUserId(int recordedByUserId) { this.recordedByUserId = recordedByUserId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}