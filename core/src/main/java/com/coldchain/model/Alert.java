package com.coldchain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Alerts")
public class Alert {

    @Id
    private long id; // Matches BIGINT

    private long temperature_log_id;
    private int shipment_id;
    private String alert_type;
    private String alert_message;
    private BigDecimal temp_at_alert;
    private BigDecimal required_min;
    private BigDecimal required_max;
    private String severity; // ENUM maps to String
    private Integer acknowledged_by; // Use Integer for nullable INT
    private LocalDateTime acknowledged_at;
    private LocalDateTime created_at;

    // --- Getters and Setters ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getTemperature_log_id() { return temperature_log_id; }
    public void setTemperature_log_id(long temp_log_id) { this.temperature_log_id = temp_log_id; }
    public int getShipment_id() { return shipment_id; }
    public void setShipment_id(int shipment_id) { this.shipment_id = shipment_id; }
    public String getAlert_type() { return alert_type; }
    public void setAlert_type(String alert_type) { this.alert_type = alert_type; }
    public String getAlert_message() { return alert_message; }
    public void setAlert_message(String alert_message) { this.alert_message = alert_message; }
    public BigDecimal getTemp_at_alert() { return temp_at_alert; }
    public void setTemp_at_alert(BigDecimal temp) { this.temp_at_alert = temp; }
    public BigDecimal getRequired_min() { return required_min; }
    public void setRequired_min(BigDecimal min) { this.required_min = min; }
    public BigDecimal getRequired_max() { return required_max; }
    public void setRequired_max(BigDecimal max) { this.required_max = max; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public Integer getAcknowledged_by() { return acknowledged_by; }
    public void setAcknowledged_by(Integer acknowledged_by) { this.acknowledged_by = acknowledged_by; }
    public LocalDateTime getAcknowledged_at() { return acknowledged_at; }
    public void setAcknowledged_at(LocalDateTime acknowledged_at) { this.acknowledged_at = acknowledged_at; }
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
}