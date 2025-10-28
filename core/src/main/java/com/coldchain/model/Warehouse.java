// File: core/src/main/java/com/coldchain/model/Warehouse.java
package com.coldchain.model;

import java.time.LocalDateTime;

public class Warehouse {
    private int warehouseId;
    private String name;
    private String location;
    private LocalDateTime createdAt;

    // Getters and Setters for all fields...
    public int getWarehouseId() { return warehouseId; }
    public void setWarehouseId(int warehouseId) { this.warehouseId = warehouseId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}