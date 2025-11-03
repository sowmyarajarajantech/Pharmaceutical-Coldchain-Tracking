package com.coldchain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "Products") // Make sure this matches your schema.sql table name
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Matches 'id INT' in schema

    private String sku;
    private String manufacturer;
    private String name;
    private String description;

    // Use BigDecimal for DECIMAL types
    private BigDecimal required_temp_min;
    private BigDecimal required_temp_max;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getRequired_temp_min() { return required_temp_min; }
    public void setRequired_temp_min(BigDecimal min) { this.required_temp_min = min; }
    public BigDecimal getRequired_temp_max() { return required_temp_max; }
    public void setRequired_temp_max(BigDecimal max) { this.required_temp_max = max; }
}