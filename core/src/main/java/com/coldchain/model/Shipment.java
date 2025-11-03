package com.coldchain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "Shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Matches 'id'

    private String shipment_code;
    private int batch_id;
    private int from_warehouse;
    private int to_warehouse;
    private LocalDateTime shipped_at;
    private LocalDateTime expected_delivery;
    private LocalDateTime actual_delivery;
    private String status;
    private LocalDateTime created_at;

    // --- Getters and Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getShipment_code() { return shipment_code; }
    public void setShipment_code(String shipment_code) { this.shipment_code = shipment_code; }
    public int getBatch_id() { return batch_id; }
    public void setBatch_id(int batch_id) { this.batch_id = batch_id; }
    public int getFrom_warehouse() { return from_warehouse; }
    public void setFrom_warehouse(int from_warehouse) { this.from_warehouse = from_warehouse; }
    public int getTo_warehouse() { return to_warehouse; }
    public void setTo_warehouse(int to_warehouse) { this.to_warehouse = to_warehouse; }
    public LocalDateTime getShipped_at() { return shipped_at; }
    public void setShipped_at(LocalDateTime shipped_at) { this.shipped_at = shipped_at; }
    public LocalDateTime getExpected_delivery() { return expected_delivery; }
    public void setExpected_delivery(LocalDateTime expected_delivery) { this.expected_delivery = expected_delivery; }
    public LocalDateTime getActual_delivery() { return actual_delivery; }
    public void setActual_delivery(LocalDateTime actual_delivery) { this.actual_delivery = actual_delivery; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
}