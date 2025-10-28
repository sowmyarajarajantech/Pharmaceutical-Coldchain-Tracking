// File: core/src/main/java/com/coldchain/model/Batch.java
package com.coldchain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Batch {
    private int batchId;
    private String productName;
    private String manufacturer;
    private LocalDate expiryDate;
    private int quantity;
    private BigDecimal requiredTemp; // Corrected from required_temp_max
    private String status;
    private LocalDateTime createdAt;

    // Getters and Setters for all fields...
    public int getBatchId() { return batchId; }
    public void setBatchId(int batchId) { this.batchId = batchId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getRequiredTemp() { return requiredTemp; }
    public void setRequiredTemp(BigDecimal requiredTemp) { this.requiredTemp = requiredTemp; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}