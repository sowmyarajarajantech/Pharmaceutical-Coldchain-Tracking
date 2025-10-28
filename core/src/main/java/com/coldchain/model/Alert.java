// File: core/src/main/java/com/coldchain/model/Alert.java
package com.coldchain.model;

import java.time.LocalDateTime;

public class Alert {
    private int alertId;
    private int shipmentId;
    private int tempLogId; // Corrected to int to match schema
    private String message;
    private String alertLevel;
    private boolean acknowledged;
    private LocalDateTime createdAt;

    // Getters and Setters for all fields...
    public int getAlertId() { return alertId; }
    public void setAlertId(int alertId) { this.alertId = alertId; }
    public int getShipmentId() { return shipmentId; }
    public void setShipmentId(int shipmentId) { this.shipmentId = shipmentId; }
    public int getTempLogId() { return tempLogId; }
    public void setTempLogId(int tempLogId) { this.tempLogId = tempLogId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getAlertLevel() { return alertLevel; }
    public void setAlertLevel(String alertLevel) { this.alertLevel = alertLevel; }
    public boolean isAcknowledged() { return acknowledged; }
    public void setAcknowledged(boolean acknowledged) { this.acknowledged = acknowledged; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}