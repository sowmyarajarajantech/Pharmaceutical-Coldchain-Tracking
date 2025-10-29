// File: core/src/main/java/com/coldchain/model/Shipment.java
package com.coldchain.model;

import java.time.LocalDateTime;

// --- ADD THESE 4 IMPORTS ---
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Shipment {

    // --- ADD THESE 2 ANNOTATIONS ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shipmentId;
    
    private int batchId;
    private int originWarehouseId;
    private int destinationWarehouseId;
    private LocalDateTime shipmentDate;
    private LocalDateTime expectedDeliveryDate;
    private LocalDateTime actualDeliveryDate;
    private String status;
    private LocalDateTime createdAt;

    // Getters and Setters...
    public int getShipmentId() { return shipmentId; }
    public void setShipmentId(int shipmentId) { this.shipmentId = shipmentId; }
    public int getBatchId() { return batchId; }
    public void setBatchId(int batchId) { this.batchId = batchId; }
    public int getOriginWarehouseId() { return originWarehouseId; }
    public void setOriginWarehouseId(int originWarehouseId) { this.originWarehouseId = originWarehouseId; }
    public int getDestinationWarehouseId() { return destinationWarehouseId; }
    public void setDestinationWarehouseId(int destinationWarehouseId) { this.destinationWarehouseId = destinationWarehouseId; }
    public LocalDateTime getShipmentDate() { return shipmentDate; }
    public void setShipmentDate(LocalDateTime shipmentDate) { this.shipmentDate = shipmentDate; }
    public LocalDateTime getExpectedDeliveryDate() { return expectedDeliveryDate; }
    public void setExpectedDeliveryDate(LocalDateTime expectedDeliveryDate) { this.expectedDeliveryDate = expectedDeliveryDate; }
    public LocalDateTime getActualDeliveryDate() { return actualDeliveryDate; }
    public void setActualDeliveryDate(LocalDateTime actualDeliveryDate) { this.actualDeliveryDate = actualDeliveryDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}