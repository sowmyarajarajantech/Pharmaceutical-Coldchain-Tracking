package com.coldchain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TemperatureLogs")
public class TemperatureLog {

    @Id
    private long id; // Matches BIGINT

    private int shipment_id;
    private LocalDateTime recorded_at;
    private BigDecimal temperature_celsius;
    private int recorded_by;
    private LocalDateTime created_at;

    // --- Getters and Setters ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public int getShipment_id() { return shipment_id; }
    public void setShipment_id(int shipment_id) { this.shipment_id = shipment_id; }
    public LocalDateTime getRecorded_at() { return recorded_at; }
    public void setRecorded_at(LocalDateTime recorded_at) { this.recorded_at = recorded_at; }
    public BigDecimal getTemperature_celsius() { return temperature_celsius; }
    public void setTemperature_celsius(BigDecimal temp) { this.temperature_celsius = temp; }
    public int getRecorded_by() { return recorded_by; }
    public void setRecorded_by(int recorded_by) { this.recorded_by = recorded_by; }
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
}